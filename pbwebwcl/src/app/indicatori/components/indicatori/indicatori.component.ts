/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';

import { Constants } from "../../../core/commons/util/constants";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { IndicatoriService } from "../../services/indicatori.service";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material/paginator";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { IndicatoreItem } from "../../commons/dto/indicatore-item";
import { ResponseGetIndicatori } from "../../commons/dto/response-get-indicatori";
import { SalvaIndicatoriRequest } from "../../commons/dto/salva-indicatori-request";
import { ValidazioneDatiIndicatoriRequest } from "../../commons/dto/validazione-dati-indicatori-request";
import { ExecResults } from "../../../cronoprogramma/commons/dto/exec-results";
import { NgForm } from "@angular/forms";
import { SharedService } from 'src/app/shared/services/shared.service';
import { MatDialog } from "@angular/material/dialog";
import { VisualizzaTestoDialogComponent } from '../visualizza-testo-dialog/visualizza-testo-dialog.component';


@Component({
  selector: 'app-indicatori',
  templateUrl: './indicatori.component.html',
  styleUrls: ['./indicatori.component.scss']
})
export class IndicatoriComponent implements OnInit {

  @Input('idProgetto') idProgetto: number;
  @Input('idBandoLinea') idBandoLinea: number;
  @Input('user') user: UserInfoSec;
  @Input('indicatoriObbligatori') indicatoriObbligatori: boolean;
  isConfermatoSuccess: boolean;
  isBandoSif: boolean;
  isReadOnly: boolean;

  displayedColumns: string[] = ['codice', 'descrizione', 'unitàDiMisura', 'valoreProgrammato', 'valoreAggiornamento', 'valoreRealizzato'];
  dataSource: MatTableDataSource<IndicatoreItem> = new MatTableDataSource<IndicatoreItem>();
  dataSourceAltri: MatTableDataSource<IndicatoreItem> = new MatTableDataSource<IndicatoreItem>();

  @ViewChild("indicatoriForm") form: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  responseGetIndicatori: ResponseGetIndicatori = { indicatoriMonitoraggio: new Array<IndicatoreItem>(), altriIndicatori: new Array<IndicatoreItem>() };

  @ViewChild("tableForm", { static: true }) ngForm: NgForm;
  @ViewChild("altriIndicatoriForm", { static: true }) altriIndicatoriForm: NgForm;

  //LOADED
  loadedIndicatori: boolean;
  loadedSave: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedIsSif: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  validazioneDatiIndicatoriRequest: ValidazioneDatiIndicatoriRequest;
  salvaIndicatoriRequest: SalvaIndicatoriRequest;

  confermaSalvataggio: boolean = false;

  errMsgs: string[] = [""];

  constructor(private configService: ConfigService, private userService: UserService, private activatedRoute: ActivatedRoute,
    private indicatoriService: IndicatoriService, private router: Router, private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadData();
  }

  trackByFn(index, item) {
    return index;
  }

  loadData() {
    this.loadedSave = false;
    this.loadedIsSif = false;
    this.userService.isBandoSif(this.idBandoLinea).subscribe(data1 => {
      this.isBandoSif = data1;

      if (this.isBandoSif) {
        this.isReadOnly = true;
      }

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

      this.loadedIsSif = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore in fase di verifica bando SIF."]);
      this.loadedIsSif = true;
    });
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

  verificaIndicatoriMonitoraggio(chiusura?: boolean) {
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
        if ((!item.valoreFinale || item.valoreFinale.length === 0) && item.valoreIniziale && item.valoreIniziale.length > 0) {
          this.ngForm.form.get('valoreFinale' + i).setErrors({ error: 'required' });
          if (!this.msg.find(m => m === "Se si inserisce un valore iniziale, é obbligatorio inserire il corrispondente valore finale."))
            this.msg.push("Se si inserisce un valore iniziale, é obbligatorio inserire il corrispondente valore finale.");
          isError = true;
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
    if (chiusura) {
      for (let tipo of tipi) {
        let el = document.getElementById('tipo' + tipo.id);
        el.classList.remove('red-color');
        let tipoValido: boolean = false;
        for (let i of tipo.indicatori) {
          if (i.valoreFinale !== null && i.valoreFinale !== undefined && i.valoreFinale.length > 0) {
            tipoValido = true;
          }
        }
        if (!tipoValido) {
          if (!this.msg.find(e => e === "Per almeno un indicatore di Monitoraggio di ciascun tipo è obbligatorio inserire un valore finale.")) {
            this.msg.push("Per almeno un indicatore di Monitoraggio di ciascun tipo è obbligatorio inserire un valore finale.");
          }
          el.classList.add('red-color');
          isError = true;
        }
      }
    }
    this.ngForm.form.markAllAsTouched();
    return isError;
  }

  verificaAltriIndicatori(chiusura?: boolean) {
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
          if (chiusura) {
            if (item.flagObbligatorio) {
              if (!item.valoreFinale || item.valoreFinale.length === 0) {
                this.altriIndicatoriForm.form.get('valoreFinaleAltri' + i).setErrors({ error: 'required' });
                item.isValoreAggiornamentoEditable = true;
                item.isValoreFinaleEditable = true;
                // if (this.isRettifica) {
                //   item.isValoreInizialeEditable = true;
                // }
                isError = true;
              }
              if (!item.valoreIniziale || item.valoreIniziale.length === 0) {
                this.altriIndicatoriForm.form.get('valoreInizialeAltri' + i).setErrors({ error: 'required' });
                item.isValoreAggiornamentoEditable = true;
                item.isValoreFinaleEditable = true;
                // if (this.isRettifica) {
                //   item.isValoreInizialeEditable = true;
                // }
                isError = true;
              }
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
  controllaDati(chiusura?: boolean) {
    this.resetMessageError();
    this.msg = [this.msg[0]];
    let isErrorMonitoraggio: boolean = this.verificaIndicatoriMonitoraggio(chiusura);
    //MANCANO ERRORI ALTRI INDICATORI
    let isErrorAltri: boolean = this.verificaAltriIndicatori();
    if (isErrorMonitoraggio || isErrorAltri) {
      this.showMessageError(this.msg);
      return true;
    }
    return false;
  }

  controllaDatiPerChiusura() {
    return this.controllaDati(true);
  }

  salva(fromParent?: boolean) {
    if ((!fromParent && !this.controllaDati()) || fromParent) {
      this.resetMessageSuccess();
      this.resetMessageError();
      this.resetMessageWarning();

      if (fromParent) {
        this.confermaSalva(true);
      } else {
        this.showMessageWarning("Confermare il salvataggio dei dati inseriti.");
        this.confermaSalvataggio = true;
      }
      //spostato validazione servizio dentro controllaDati()
      //this.indicatoriService.controllaDatiPerSalvataggioGestione()
    }
  }

  confermaSalva(fromParent?: boolean) {
    this.loadedSave = true;
    this.salvaIndicatoriRequest = { indicatoriMonitoraggio: this.responseGetIndicatori.indicatoriMonitoraggio, altriIndicatori: this.responseGetIndicatori.altriIndicatori, idProgetto: this.idProgetto };
    this.subscribers.save = this.indicatoriService.saveIndicatoriGestione(this.salvaIndicatoriRequest)
      .subscribe(data => {
        if (data) {
          console.log(data);
          if (data.successo) {
            if (fromParent) {
              this.isConfermatoSuccess = true;
            } else {
              this.resetMessageSuccess();
              this.resetMessageError();
              //this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INDICATORI + "/cronoprogramma", data.messagi[0], { action: "SUCCESS" }]);
              this.showMessageSuccess(data.messaggi[0]);
              this.confermaSalvataggio = false;
              this.isMessageWarningVisible = false;
              this.showMessageSuccess(data.messaggi[0]);
              setTimeout(() => {
                if (this.indicatoriObbligatori || (!this.indicatoriObbligatori && this.isTableIndicatoriCompilata())) {
                  this.controllaDatiPerChiusura();
                }
              }, 1);
            }
          } else {
            this.resetMessageSuccess();
            this.resetMessageError();

            this.showMessageError(data.messaggi);
            this.confermaSalvataggio = false;
            this.isMessageWarningVisible = false;
            this.showMessageError(data.messaggi);
          }
        }
        this.loadedSave = false;
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

  isTableIndicatoriCompilata() {
    let isRigaCompilata: boolean = false;
    let i: number = 0;
    for (let ind of this.responseGetIndicatori.indicatoriMonitoraggio) {
      if (!ind.isTipoIndicatore) {
        if (!this.ngForm.form.get('valoreIniziale' + i) || !this.ngForm.form.get('valoreIniziale' + i).disabled) {
          if (ind.valoreIniziale !== null && ind.valoreIniziale !== undefined && ind.valoreIniziale.length > 0) {
            isRigaCompilata = true;
            break;
          }
        }
        if (!this.ngForm.form.get('valoreAggiornamento' + i) || !this.ngForm.form.get('valoreAggiornamento' + i).disabled) {
          if (ind.valoreAggiornamento !== null && ind.valoreAggiornamento !== undefined && ind.valoreAggiornamento.length > 0) {
            isRigaCompilata = true;
            break;
          }
        }
        if (!this.ngForm.form.get('valoreRealizzato' + i) || !this.ngForm.form.get('valoreRealizzato' + i).disabled) {
          if (ind.valoreFinale !== null && ind.valoreFinale !== undefined && ind.valoreFinale.length > 0) {
            isRigaCompilata = true;
            break;
          }
        }
      }
      i++;
    }
    return isRigaCompilata;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollInd');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollInd');
    element.scrollIntoView();
  }

  showMessageError(msgs: Array<string>) {
    this.messageError = msgs;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollInd');
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


    if (!this.loadedIndicatori || this.loadedSave || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_INDICATORI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  // To manage input losing focus - when using angular material table with more than one html input inside, each using 2 way data binding ([(ngModel)])
  updateValoreIniziale(codIgrue: any, vI: HTMLInputElement) {
    let ind: Array<IndicatoreItem>;
    ind = this.responseGetIndicatori.indicatoriMonitoraggio.map(item => {
      item.codIgrue === codIgrue ? item.valoreIniziale = vI.value : item.valoreIniziale = item.valoreIniziale;
      return item;
    });
    this.responseGetIndicatori.indicatoriMonitoraggio = ind;
    this.dataSource = new MatTableDataSource<IndicatoreItem>();
    this.dataSource.data = this.responseGetIndicatori.indicatoriMonitoraggio;
  }

  updateValoreAggiornamento(codIgrue: any, vA: HTMLInputElement) {
    let ind: Array<IndicatoreItem>;
    ind = this.responseGetIndicatori.indicatoriMonitoraggio.map(item => {
      item.codIgrue === codIgrue ? item.valoreAggiornamento = vA.value : item.valoreAggiornamento = item.valoreAggiornamento;
      return item;
    });
    this.responseGetIndicatori.indicatoriMonitoraggio = ind;
    this.dataSource = new MatTableDataSource<IndicatoreItem>();
    this.dataSource.data = this.responseGetIndicatori.indicatoriMonitoraggio;
  }

  updateValoreFinale(codIgrue: any, vF: HTMLInputElement) {
    let ind: Array<IndicatoreItem>;
    ind = this.responseGetIndicatori.indicatoriMonitoraggio.map(item => {
      item.codIgrue === codIgrue ? item.valoreFinale = vF.value : item.valoreFinale = item.valoreFinale;
      return item;
    });
    this.responseGetIndicatori.indicatoriMonitoraggio = ind;
    this.dataSource = new MatTableDataSource<IndicatoreItem>();
    this.dataSource.data = this.responseGetIndicatori.indicatoriMonitoraggio;
  }

  numberOnly(e: any) {
    const charCode = e.which ? e.which : e.keyCode;
    //allow dot (charCode=44) and comma(charCode=46)
    if (charCode > 31 && charCode != 44 && charCode != 46 && (charCode < 48 || charCode > 57)) {
      e.preventDefault();
      return false;
    } else {
      return true;
    }
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
