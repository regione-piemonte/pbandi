/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { MatTableDataSource } from "@angular/material/table";
import { IndicatoreItem } from "../../commons/dto/indicatore-item";
import { NgForm } from "@angular/forms";
import { MatPaginator } from "@angular/material/paginator";
import { ResponseGetIndicatori } from "../../commons/dto/response-get-indicatori";
import { ValidazioneDatiIndicatoriRequest } from "../../commons/dto/validazione-dati-indicatori-request";
import { SalvaIndicatoriRequest } from "../../commons/dto/salva-indicatori-request";
import { ExecResults } from "../../../cronoprogramma/commons/dto/exec-results";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { IndicatoriService } from "../../services/indicatori.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { Constants } from "../../../core/commons/util/constants";
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-indicatori-avvio',
  templateUrl: './indicatori-avvio.component.html',
  styleUrls: ['./indicatori-avvio.component.scss']
})
export class IndicatoriAvvioComponent implements OnInit {
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  displayedColumns: string[] = ['codice', 'descrizione', 'unitàDiMisura', 'valoreProgrammato', 'valoreAggiornamento', 'valoreRealizzato'];
  dataSource: MatTableDataSource<IndicatoreItem> = new MatTableDataSource<IndicatoreItem>();
  dataSourceAltri: MatTableDataSource<IndicatoreItem> = new MatTableDataSource<IndicatoreItem>();

  @ViewChild("indicatoriForm", { static: true }) ngForm: NgForm;
  @ViewChild("altriIndicatoriForm", { static: true }) altriIndicatoriForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  responseGetIndicatori: ResponseGetIndicatori = { indicatoriMonitoraggio: new Array<IndicatoreItem>(), altriIndicatori: new Array<IndicatoreItem>() };

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedIndicatori: boolean;
  loadedSave: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  validazioneDatiIndicatoriRequest: ValidazioneDatiIndicatoriRequest;
  salvaIndicatoriRequest: SalvaIndicatoriRequest;
  execResults: ExecResults;

  confermaSalvataggio: boolean = false;
  salvataggioTerminato: boolean;
  hasValidationError: boolean;

  errMsgs: string[] = [""];

  constructor(private configService: ConfigService, private userService: UserService, private activatedRoute: ActivatedRoute,
    private indicatoriService: IndicatoriService, private dialog: MatDialog, private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService, private router: Router) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.loadData();

          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_INDICATORI_AVVIO;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  trackByFn(index, item) {
    return index;
  }

  loadData() {
    this.loadedSave = false;
    // Codice progetto
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.indicatoriService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    // Indicatori
    this.loadedIndicatori = false;
    this.subscribers.codice = this.indicatoriService.getIndicatoriAvvio(this.idProgetto).subscribe(res => {
      if (res) {
        this.responseGetIndicatori = res;
        this.dataSource = new MatTableDataSource<IndicatoreItem>();
        this.dataSource.data = this.responseGetIndicatori.indicatoriMonitoraggio;

        this.dataSourceAltri = new MatTableDataSource<IndicatoreItem>();
        this.dataSourceAltri.data = this.responseGetIndicatori.altriIndicatori;
      }
      this.loadedIndicatori = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedIndicatori = true;
    })
  }

  setValoreIniziale(item: IndicatoreItem) {
    let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreIniziale);
    if (imp !== null) {
      item.valoreIniziale = this.sharedService.formatValue(imp.toString());
    }
  }

  setValoreAggiornamento(item: IndicatoreItem) {
    let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreAggiornamento);
    if (imp !== null) {
      item.valoreAggiornamento = this.sharedService.formatValue(imp.toString());
    }
  }

  setValoreFinale(item: IndicatoreItem) {
    let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreFinale);
    if (imp !== null) {
      item.valoreFinale = this.sharedService.formatValue(imp.toString());
    }
  }

  checkRequiredForm(form: any, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  verificaIndicatoriMonitoraggioAvvio() {
    let isError: boolean;
    // PBANDI-1573: verifico che il valore degli indicatori sia strettamente
    // maggiore di zero
    // PBANDI-1587: lunghezza indicatori
    let i: number = 0;
    for (let item of this.responseGetIndicatori.indicatoriMonitoraggio) {
      if (!item.isTipoIndicatore && item.valoreIniziale) {
        let impIn = this.sharedService.getNumberFromFormattedString(item.valoreIniziale);
        if (impIn <= 0 || impIn > 99999999999.99) {
          this.ngForm.form.get('valoreIniziale' + i).setErrors({ error: 'nonAmmesso' });
          isError = true;
        }
      }
      i++;
    }
    // Raggruppo gli indicatori per tipo
    let tipi: Array<{ id: number, indicatori: Array<IndicatoreItem> }> = new Array<{ id: number, indicatori: Array<IndicatoreItem> }>();
    this.responseGetIndicatori.indicatoriMonitoraggio.forEach(item => {
      let tipo = tipi.find(t => t.id === item.idTipoIndicatore);
      if (!tipo) {
        let ind = new Array<IndicatoreItem>();
        ind.push(item);
        tipi.push({ id: item.idTipoIndicatore, indicatori: ind });
      } else {
        tipo.indicatori.push(item);
      }
    });
    for (let tipo of tipi) {
      let el = document.getElementById('tipo' + tipo.id);
      el.classList.remove('red-color');
      let tipoValido: boolean = false;
      for (let item of tipo.indicatori) {
        if (item.valoreIniziale !== null && item.valoreIniziale !== undefined && item.valoreIniziale.length > 0) {
          tipoValido = true;
          break;
        }
      }
      if (!tipoValido) {
        if (!this.msg.find(e => e === "Per ogni tipo di indicatore di Monitoraggio è obbligatorio inserire almeno un valore iniziale.")) {
          this.msg.push("Per ogni tipo di indicatore di Monitoraggio è obbligatorio inserire almeno un valore iniziale.");
        }
        el.classList.add('red-color');
        isError = true;
      }
    }
    this.ngForm.form.markAllAsTouched();
    return isError;
  }

  verificaAltriIndicatoriAvvio() {
    let isError: boolean;
    let i: number = 0;
    for (let item of this.responseGetIndicatori.altriIndicatori) {
      if (!item.isTipoIndicatore) {
        if (item.flagNonApplicabile) {
          item.valoreIniziale = "NA";
          item.valoreAggiornamento = "NA";
          item.valoreFinale = "NA";
          item.isValoreAggiornamentoEditable = false;
          item.isValoreInizialeEditable = false;
          item.isValoreFinaleEditable = false;
        } else {
          if (item.flagObbligatorio) {
            if (!item.valoreIniziale || item.valoreIniziale.length === 0) {
              this.altriIndicatoriForm.form.get('valoreInizialeAltri' + i).setErrors({ error: 'required' });
              item.isValoreInizialeEditable = true;
              isError = true;
            }
          }
          // PBANDI-1573: verifico che il valore degli indicatori sia
          // strettamente maggiore di zero
          // PBANDI-1587: lunghezza indicatori
          if (item.valoreIniziale && item.valoreIniziale.length > 0 && item.valoreIniziale !== "NA") {
            let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreIniziale);
            if (imp && (imp < 0 || imp > 99999999999.99)) {
              this.altriIndicatoriForm.form.get('valoreInizialeAltri' + i).setErrors({ error: 'nonAmmesso' });
              item.isValoreInizialeEditable = true;
              isError = true;
            }
          }
        }
      }
      i++;
    }
    this.altriIndicatoriForm.form.markAllAsTouched();
    return isError;
  }

  msg: string[] = ["ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire."];
  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.msg = [this.msg[0]];
    this.hasValidationError = false;

    // verifico indicatori monitoraggio
    let isErrorMonitoraggio: boolean = this.verificaIndicatoriMonitoraggioAvvio();

    // manca la verifica altri indicatori
    let isErrorAltri: boolean = this.verificaAltriIndicatoriAvvio();

    if (isErrorMonitoraggio || isErrorAltri) {
      this.hasValidationError = true;
      this.showMessageError(this.msg);
    }
  }

  salva() {
    this.validate();
    if (!this.hasValidationError) {
      this.resetMessageSuccess();
      this.resetMessageError();
      this.resetMessageWarning();

      this.showMessageWarning("Confermare il salvataggio dei dati inseriti.");
      this.confermaSalvataggio = true;

      //spostato validazione servizio dentro validate()
      //this.indicatoriService.controllaDatiPerSalvataggioAvvio()
    }
  }

  confermaSalva() {
    this.loadedSave = true;
    this.salvataggioTerminato = false;

    this.salvaIndicatoriRequest = { indicatoriMonitoraggio: this.responseGetIndicatori.indicatoriMonitoraggio, altriIndicatori: this.responseGetIndicatori.altriIndicatori, idProgetto: this.idProgetto };
    this.subscribers.save = this.indicatoriService.saveIndicatoriAvvio(this.salvaIndicatoriRequest)
      .subscribe(data => {
        if (data) {
          console.log(data);
          if (data.successo) {
            this.resetMessageSuccess();
            this.resetMessageError();
            //this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INDICATORI_AVVIO + "/cronoprogramma", data.messagi[0], { action: "SUCCESS" }]);
            this.showMessageSuccess(data.messaggi[0]);
            this.confermaSalvataggio = false;
            this.isMessageWarningVisible = false;
            this.loadedSave = false;
            this.salvataggioTerminato = true;
            this.showMessageSuccess(data.messaggi[0]);
          } else {
            this.resetMessageSuccess();
            this.resetMessageError();

            this.showMessageError(data.messaggi);
            this.confermaSalvataggio = false;
            this.isMessageWarningVisible = false;
            this.loadedSave = false;
            this.showMessageError(data.messaggi);
          }
        }

      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.confermaSalvataggio = false;

        this.resetMessageSuccess();
        this.resetMessageError();
        this.resetMessageWarning();

        this.showMessageError(["Errore nel salvataggio degli indicatori."]);
        this.loadedSave = false;
      });

  }


  annulla() {
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.resetMessageError();
    this.confermaSalvataggio = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }


  showMessageError(msgs: Array<string>) {
    this.messageError = msgs;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }


  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.errMsgs = [];
    this.isMessageErrorVisible = false;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {


    if (!this.loadedCodiceProgetto || !this.loadedIndicatori || this.loadedSave || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_INDICATORI_AVVIO).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  isEmpty(obj) {
    for (var key in obj) {
      if (obj.hasOwnProperty(key))
        return false;
    }
    return true;
  }

}
