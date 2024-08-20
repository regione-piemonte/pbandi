/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';

import { Constants } from "../../../core/commons/util/constants";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { IndicatoriService } from "../../services/indicatori.service";
import { MatTableDataSource } from "@angular/material/table";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { IndicatoreItem } from "../../commons/dto/indicatore-item";
import { ResponseGetIndicatori } from "../../commons/dto/response-get-indicatori";
import { SalvaIndicatoriRequest } from "../../commons/dto/salva-indicatori-request";
import { ValidazioneDatiIndicatoriRequest } from "../../commons/dto/validazione-dati-indicatori-request";
import { NgForm } from "@angular/forms";
import { SharedService } from 'src/app/shared/services/shared.service';
import { MatDialog } from "@angular/material/dialog";
import { VisualizzaTestoDialogComponent } from "../../../shared/components/visualizza-testo-dialog/visualizza-testo-dialog.component";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { Indicatori2Service } from '../../services/indicatori2.service';


@Component({
  selector: 'app-indicatori',
  templateUrl: './indicatori.component.html',
  styleUrls: ['./indicatori.component.scss']
})
export class IndicatoriComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isRettifica: boolean;
  isReadOnly: boolean;
  esisteCFP: boolean;
  esisteDsFinale: boolean;
  isBandoSif: boolean;

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
  loadedInizializza: boolean;
  loadedCodiceProgetto: boolean;
  loadedIndicatori: boolean;
  loadedSave: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  validazioneDatiIndicatoriRequest: ValidazioneDatiIndicatoriRequest;
  salvaIndicatoriRequest: SalvaIndicatoriRequest;

  confermaSalvataggio: boolean = false;

  errMsgs: string[] = [""];

  constructor(private configService: ConfigService, private userService: UserService, private activatedRoute: ActivatedRoute,
    private indicatoriService: IndicatoriService, private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService, private dialog: MatDialog, private indicatori2Service: Indicatori2Service, private router: Router) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.isBandoSif = params['sif'] ? true : false;
            if (this.isBandoSif) {
              this.isReadOnly = true;
            }
            this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
              this.idOperazione = +params['id'];
              if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RETTIFICA_INDICATORI) {
                this.isRettifica = true;
                if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADA_OPE_MASTER
                  || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT
                  || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST
                  || this.user.codiceRuolo === Constants.CODICE_RUOLO_GDF
                  || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADC_CERT) {
                  this.isReadOnly = true;
                }
              }
              this.loadedInizializza = false;
              this.subscribers.codice = this.indicatori2Service.inizializzaIndicatori(this.idProgetto).subscribe(data => {
                if (data) {
                  this.esisteCFP = data.esisteCFP;
                  this.esisteDsFinale = data.esisteDsFinale;
                }
                this.loadedInizializza = true;
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.loadedInizializza = true;
              });
              this.loadData();
            });
          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_INDICATORI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  trackByFn(index) {
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
    this.subscribers.codice = this.indicatoriService.getIndicatori(this.idProgetto, this.isBandoSif).subscribe(res => {
      if (res) {
        this.responseGetIndicatori = res;
        this.responseGetIndicatori.indicatoriMonitoraggio.forEach(i => {
          this.setValoreIniziale(i);
          this.setValoreAggiornamento(i);
          this.setValoreFinale(i);
        });
        this.dataSource = new MatTableDataSource<IndicatoreItem>();
        this.dataSource.data = this.responseGetIndicatori.indicatoriMonitoraggio;

        this.responseGetIndicatori.altriIndicatori.forEach(i => {
          this.setValoreIniziale(i);
          this.setValoreAggiornamento(i);
          this.setValoreFinale(i);
        });
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

  verificaIndicatoriMonitoraggio() {
    let isError: boolean;
    let i: number = 0;
    for (let item of this.responseGetIndicatori.indicatoriMonitoraggio) {
      if (!item.isTipoIndicatore) {
        if (item.valoreIniziale && item.valoreAggiornamento && item.valoreIniziale.length > 0 && item.valoreAggiornamento !== "NA") {
          let imp = this.sharedService.getNumberFromFormattedString(item.valoreIniziale);
          if (imp !== null && (imp <= 0 || imp > 99999999999.99)) {
            this.ngForm.form.get('valoreIniziale' + i).setErrors({ error: 'nonAmmesso' });
            isError = true;
          }
        }
        if (item.valoreAggiornamento && item.valoreAggiornamento.length > 0 && item.valoreAggiornamento !== "NA") {
          let imp = this.sharedService.getNumberFromFormattedString(item.valoreAggiornamento);
          if (imp <= 0 || imp > 99999999999.99) {
            this.ngForm.form.get('valoreAggiornamento' + i).setErrors({ error: 'nonAmmesso' });
            isError = true;
          }
        }
        if (item.valoreFinale && item.valoreFinale.length > 0 && item.valoreFinale !== "NA") {
          let imp = this.sharedService.getNumberFromFormattedString(item.valoreFinale);
          if (imp <= 0 || imp > 99999999999.99) {
            this.ngForm.form.get('valoreFinale' + i).setErrors({ error: 'nonAmmesso' });
            isError = true;
          }
        }
        if (item.valoreFinale && item.valoreFinale.length > 0 && (!item.valoreIniziale || item.valoreIniziale.length === 0)) {
          this.ngForm.form.get('valoreIniziale' + i).setErrors({ error: 'required' });
          if (!this.msg.find(m => m === "Se si inserisce un valore finale, é obbligatorio inserire il corrispondente valore iniziale."))
            this.msg.push("Se si inserisce un valore finale, é obbligatorio inserire il corrispondente valore iniziale.");
          isError = true;
          item.isValoreInizialeEditable = true;
        }
        // Jira PBANDI-2814: deve esserci un valore finale dove c'è un valore iniziale.
        if ((this.isBeneficiario() && this.esisteDsFinale) || (!this.isBeneficiario() && this.esisteCFP)) {
          if ((!item.valoreFinale || item.valoreFinale.length === 0) && item.valoreIniziale && item.valoreIniziale.length > 0) {
            this.ngForm.form.get('valoreFinale' + i).setErrors({ error: 'required' });
            if (!this.msg.find(m => m === "Se si inserisce un valore iniziale, é obbligatorio inserire il corrispondente valore finale."))
              this.msg.push("Se si inserisce un valore iniziale, é obbligatorio inserire il corrispondente valore finale.");
            isError = true;
          }
        }
      }
      i++;
    }
    // Jira PBANDI-2814: deve esserci almeno un valore iniziale (colonna 'valore programmato') 
    // per ogni tipo di indicatore, sempre per tutti i progetti (cioè con o senza una comunicazione di fine progetto).
    // Raggruppo gli indicatori per tipo
    let tipi: Array<{ id: number, indicatori: Array<IndicatoreItem> }> = new Array<{ id: number, indicatori: Array<IndicatoreItem> }>();
    this.responseGetIndicatori.indicatoriMonitoraggio.forEach(i => {
      let tipo = tipi.find(t => t.id === i.idTipoIndicatore);
      if (!tipo) {
        let ind = new Array<IndicatoreItem>();
        ind.push(i);
        tipi.push({ id: i.idTipoIndicatore, indicatori: ind });
      } else {
        tipo.indicatori.push(i);
      }
    });
    for (let tipo of tipi) {
      let el = document.getElementById('tipo' + tipo.id);
      el.classList.remove('red-color');
      let tipoValido: boolean = false;
      for (let i of tipo.indicatori) {
        if (i.valoreIniziale !== null && i.valoreIniziale !== undefined && i.valoreIniziale.length > 0) {
          tipoValido = true;
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

  verificaAltriIndicatori() {
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
          // PBANDI-1573: verifico che il valore degli indicatori sia strettamente maggiore di zero
          // PBANDI-1587: lunghezza indicatori
          if (item.valoreIniziale && item.valoreIniziale.length > 0 && item.valoreIniziale !== "NA") {
            let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreIniziale);
            if (imp && (imp < 0 || imp > 99999999999.99)) {
              this.altriIndicatoriForm.form.get('valoreInizialeAltri' + i).setErrors({ error: 'nonAmmesso' });
              item.isValoreAggiornamentoEditable = true;
              item.isValoreFinaleEditable = true;
              // if (this.isRettifica) {
              //   item.isValoreInizialeEditable = true;
              // }
              isError = true;
            }
          }
          if (item.valoreAggiornamento && item.valoreAggiornamento.length > 0 && item.valoreAggiornamento !== "NA") {
            let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreAggiornamento);
            if (imp && (imp < 0 || imp > 99999999999.99)) {
              this.altriIndicatoriForm.form.get('valoreAggiornamentoAltri' + i).setErrors({ error: 'nonAmmesso' });
              item.isValoreAggiornamentoEditable = true;
              item.isValoreFinaleEditable = true;
              // if (this.isRettifica) {
              //   item.isValoreInizialeEditable = true;
              // }
              isError = true;
            }
          }
          if (item.valoreFinale && item.valoreFinale.length > 0 && item.valoreFinale !== "NA") {
            let imp: number = this.sharedService.getNumberFromFormattedString(item.valoreFinale);
            if (imp && (imp < 0 || imp > 99999999999.99)) {
              this.altriIndicatoriForm.form.get('valoreFinaleAltri' + i).setErrors({ error: 'nonAmmesso' });
              item.isValoreAggiornamentoEditable = true;
              item.isValoreFinaleEditable = true;
              // if (this.isRettifica) {
              //   item.isValoreInizialeEditable = true;
              // }
              isError = true;
            }
          }
          if (item.valoreFinale && item.valoreFinale.length > 0 && (!item.valoreIniziale || item.valoreIniziale.length === 0)) {
            this.altriIndicatoriForm.form.get('valoreInizialeAltri' + i).setErrors({ error: 'required' });
            item.isValoreAggiornamentoEditable = true;
            item.isValoreFinaleEditable = true;
            item.isValoreInizialeEditable = true;
            isError = true;
          }
        }
      }
      i++;
    }
    this.altriIndicatoriForm.form.markAllAsTouched();
    return isError;
  }

  msg: string[] = ["ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire."];
  controllaDati() {
    this.resetMessageError();
    this.msg = [this.msg[0]];
    let isErrorMonitoraggio: boolean = this.verificaIndicatoriMonitoraggio();
    //MANCANO ERRORI ALTRI INDICATORI
    let isErrorAltri: boolean = this.verificaAltriIndicatori();
    if (isErrorMonitoraggio || isErrorAltri) {
      this.showMessageError(this.msg);
      return true;
    }
    return false;
  }

  salva() {
    if (!this.controllaDati()) {
      this.resetMessageSuccess();
      this.resetMessageError();
      this.resetMessageWarning();

      this.showMessageWarning("Confermare il salvataggio dei dati inseriti.");
      this.confermaSalvataggio = true;

      //spostato validazione servizio dentro controllaDati()
      //this.indicatoriService.controllaDatiPerSalvataggioGestione()
    }
  }

  confermaSalva() {
    this.loadedSave = true;
    this.salvaIndicatoriRequest = { indicatoriMonitoraggio: this.responseGetIndicatori.indicatoriMonitoraggio, altriIndicatori: this.responseGetIndicatori.altriIndicatori, idProgetto: this.idProgetto };
    this.subscribers.save = this.indicatoriService.saveIndicatoriGestione(this.salvaIndicatoriRequest)
      .subscribe(data => {
        if (data) {
          console.log(data);
          if (data.successo) {
            this.resetMessageSuccess();
            this.resetMessageError();
            //this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INDICATORI + "/cronoprogramma", data.messagi[0], { action: "SUCCESS" }]);
            this.showMessageSuccess(data.messaggi[0]);
            this.confermaSalvataggio = false;
            this.isMessageWarningVisible = false;
            this.loadedSave = false;
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

  isBeneficiario() {
    if (this.user && (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA)) {
      return true;
    }
    return false;
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
    this.isMessageErrorVisible = false;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedIndicatori || this.loadedSave || !this.loadedChiudiAttivita
      || !this.loadedInizializza) {
      return true;
    }
    return false;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      this.isRettifica ? Constants.DESCR_BREVE_TASK_RETTIFICA_INDICATORI : Constants.DESCR_BREVE_TASK_INDICATORI).subscribe(() => {
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

  showDialog(title: string, info: string, codIgrue: string) {
    let dialogRef = this.dialog.open(VisualizzaTestoDialogComponent, {
      width: '40%',
      data: {
        codIgrue: codIgrue,
        title: title,
        message: info
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.dialog.closeAll();
      }
    });
  }
}
