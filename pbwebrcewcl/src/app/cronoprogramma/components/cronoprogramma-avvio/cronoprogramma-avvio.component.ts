/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { TipoOperazioneDTO } from "../../commons/dto/tipo-operazione-dto";
import { Iter } from "../../commons/dto/iter";
import { CronoprogrammaItem } from "../../commons/dto/cronoprogramma-item";
import { MotivoScostamento } from "../../commons/dto/motivo-scostamento";
import { MatTableDataSource } from "@angular/material/table";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { CronoprogrammaService } from "../../services/cronoprogramma.service";
import { ActivatedRoute, Router } from "@angular/router";
import { SalvaFasiMonitoraggioGestioneRequest } from "../../commons/request/salva-fasi-monitoraggio-gestione";
import { Constants } from "../../../core/commons/util/constants";
import { NgForm } from "@angular/forms";
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-cronoprogramma-avvio',
  templateUrl: './cronoprogramma-avvio.component.html',
  styleUrls: ['./cronoprogramma-avvio.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CronoprogrammaAvvioComponent implements OnInit {

  idProgetto: number;
  idBando: number;
  codiceProgetto: string;
  user: UserInfoSec;
  programmazione: string;
  tipoOperazione: TipoOperazioneDTO;
  iter: Iter;
  isBR30: boolean;
  isBR56: boolean;

  fasiMonitoraggio: Array<CronoprogrammaItem>;
  fasiMonitoraggio1: Array<CronoprogrammaItem>;
  fasiMonitoraggioNew: Array<CronoprogrammaItem> = new Array<CronoprogrammaItem>();
  motiviScostamento: Array<MotivoScostamento>;
  dataConcessione: Date;
  dataPresentazione: Date;

  displayedColumns: string[] = ['fase', 'dataInizioPrevista', 'dataFinePrevista'];
  dataSource: MatTableDataSource<CronoprogrammaItem> = new MatTableDataSource<CronoprogrammaItem>();

  @ViewChild("cronoForm", { static: true }) ngForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedProgrammazione: boolean;
  loadedTipoOperazione: boolean;
  loadedFasiMonitoraggio: boolean;
  loadedIter: boolean;
  loadedSave: boolean;
  loadedDtConcessione: boolean;
  loadedDtPresentazioneDomanda: boolean;
  loadedMotivoScostamento: boolean;
  loadedRegolaBR30: boolean;
  loadedRegolaBR56: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  dateFormat: any = null;
  confermaSalvataggio: boolean = false;
  hasValidationError: boolean;
  salvataggioTerminato: boolean;

  errMsgs: string[] = [""];

  constructor(
    private router: Router,
    private configService: ConfigService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private cronoprogrammaService: CronoprogrammaService,
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private changeDetectorRefs: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.idBando = +params['idBando'];
            this.loadData();
          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_CRONOPROGRAMMA_AVVIO;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.cronoprogrammaService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    this.loadedRegolaBR30 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR30").subscribe(res => {
      if (res) {
        this.isBR30 = res;
      }
      this.loadedRegolaBR30 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR30 = true;
    });
    this.loadedRegolaBR56 = false;
    this.subscribers.regolaBR56 = this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR56").subscribe(res => {
      if (res) {
        this.isBR56 = res;
      }
      this.loadedRegolaBR56 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR56 = true;
    });
    this.loadedProgrammazione = false;
    this.subscribers.codice = this.cronoprogrammaService.getProgrammazioneByIdProgetto(this.idProgetto).subscribe(data1 => {
      if (data1) {
        //tipoOperazione
        this.programmazione = data1;
        this.loadedTipoOperazione = false;
        this.subscribers.codice = this.cronoprogrammaService.getTipoOperazione(this.idProgetto, this.programmazione).subscribe(data2 => {
          if (data2) {
            this.tipoOperazione = data2;
          }
          this.loadedTipoOperazione = true;

          //iter
          this.loadedIter = false;
          this.subscribers.codice = this.cronoprogrammaService.getIter(data2.idTipoOperazione, this.programmazione).subscribe(data3 => {
            if (data3) {
              this.iter = data3[0];

              //fasiMonitoraggioGestione
              this.loadedFasiMonitoraggio = false;
              this.subscribers.codice = this.cronoprogrammaService.getFasiMonitoraggioAvvio(this.idProgetto, this.programmazione, this.iter.idIter).subscribe(data6 => {
                if (data6) {
                  console.log(data6);
                  this.fasiMonitoraggio = data6;
                  this.fasiMonitoraggio1 = data6;

                  this.dataSource = new MatTableDataSource<CronoprogrammaItem>();
                  this.dataSource.data = this.fasiMonitoraggio;
                  this.changeDetectorRefs.detectChanges();
                }
                this.loadedFasiMonitoraggio = true;
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.loadedFasiMonitoraggio = true;
              });

            }
            this.loadedIter = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedIter = true;
          });


        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedTipoOperazione = true;
        });

        //data concessione e data presentazione domanda
        this.loadedDtConcessione = false;
        this.subscribers.codice = this.cronoprogrammaService.getDtConcessione(this.idProgetto).subscribe(data4 => {
          this.dataConcessione = data4;
          this.loadedDtConcessione = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedDtConcessione = true;
        });

        this.loadedDtPresentazioneDomanda = false;
        this.subscribers.codice = this.cronoprogrammaService.getDtPresentazioneDomanda(this.idProgetto).subscribe(data5 => {
          this.dataPresentazione = data5;
          this.loadedDtPresentazioneDomanda = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedDtPresentazioneDomanda = true;
        });


        //motivi scostamento
        this.loadedMotivoScostamento = false;
        this.subscribers.codice = this.cronoprogrammaService.getMotiviScostamento().subscribe(data7 => {
          if (data7) {
            console.log(data7);
            this.motiviScostamento = data7;
          }
          this.loadedMotivoScostamento = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedFasiMonitoraggio = true;
        });
      }
      this.loadedProgrammazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);

      this.loadedProgrammazione = true;
    });
  }

  salva() {
    this.validate();
    if (!this.hasValidationError) {
      this.loadedSave = true;
      this.fasiMonitoraggio.forEach(val => this.fasiMonitoraggioNew.push(Object.assign({}, val)));
      this.fasiMonitoraggioNew.map(r => {
        r.dtInizioPrevista = r.dtInizioPrevista ? this.sharedService.formatDateToString(this.sharedService.parseDate(r.dtInizioPrevista)) : null;
        r.dtFinePrevista = r.dtFinePrevista ? this.sharedService.formatDateToString(this.sharedService.parseDate(r.dtFinePrevista)) : null;
        return r;
      });
      this.showMessageWarning("Confermare il salvataggio dei dati.");
      this.confermaSalvataggio = true;
      this.loadedSave = false;
      //spostato la validazione di questo servizio dentro this.controllaDati()
      //this.cronoprogrammaService.validateDatiCronoProgrammaAvvio()
    }
  }

  confermaSalva() {
    console.log(this.fasiMonitoraggio)
    this.salvataggioTerminato = false;
    this.loadedSave = true;
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();

    this.subscribers.save = this.cronoprogrammaService.salvaFasiMonitoraggioAvvio(
      new SalvaFasiMonitoraggioGestioneRequest(this.idProgetto, this.iter.idIter, this.fasiMonitoraggioNew, this.tipoOperazione.idTipoOperazione))
      .subscribe(data => {
        if (data) {
          console.log(data);
          if (data.successo) {
            this.showMessageSuccess(data.messaggi[0]);
            this.confermaSalvataggio = false;
            this.isMessageWarningVisible = false;
            this.loadedSave = false;
            this.salvataggioTerminato = true;
            this.showMessageSuccess(data.messaggi[0]);
          } else {
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
        this.isMessageWarningVisible = false;
        this.showMessageError(["Errore nel salvataggio della cronoprgramma."]);
        this.loadedSave = false;
      });

  }

  checkRequiredForm(form: any, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllMarkAsTouched() {
    Object.keys(this.ngForm.form.controls).forEach(key => {
      if (this.ngForm.form.controls[key]) {
        this.ngForm.form.controls[key].markAsTouched();
      }
    });
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    let msg = ["ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire."];
    this.hasValidationError = false;
    for (let i = 0; i < this.fasiMonitoraggio.length; i++) {
      if (this.fasiMonitoraggio[i].flagObbligatorio) {
        this.checkRequiredForm(this.ngForm, "dataInizioPrevista" + i);
        this.checkRequiredForm(this.ngForm, "dataFinePrevista" + i);
      } else {
        if (this.fasiMonitoraggio[i].dtInizioPrevista && !this.fasiMonitoraggio[i].dtFinePrevista) {
          this.ngForm.form.get('dataFinePrevista' + i).setErrors({ error: 'required' });
          this.hasValidationError = true;
          if (!msg.find(m => m === "Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.")) {
            msg.push("Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.");
          }
        }
        if (this.fasiMonitoraggio[i].dtFinePrevista && !this.fasiMonitoraggio[i].dtInizioPrevista) {
          this.ngForm.form.get('dataInizioPrevista' + i).setErrors({ error: 'required' });
          this.hasValidationError = true;
          if (!msg.find(m => m === "Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.")) {
            msg.push("Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.");
          }
        }
      }
      if (this.fasiMonitoraggio[i].dtInizioPrevista && this.fasiMonitoraggio[i].dtFinePrevista) {
        if (this.sharedService.parseDate(this.fasiMonitoraggio[i].dtFinePrevista) < this.sharedService.parseDate(this.fasiMonitoraggio[i].dtInizioPrevista)) {
          this.ngForm.form.get('dataInizioPrevista' + i).setErrors({ error: 'incoerenti' });
          this.ngForm.form.get('dataFinePrevista' + i).setErrors({ error: 'incoerenti' });
          if (!msg.find(m => m === "Una data fine prevista deve essere uguale o successiva alla data di inizio prevista.")) {
            msg.push("Una data fine prevista deve essere uguale o successiva alla data di inizio prevista.");
          }
          this.hasValidationError = true;
        }
      }
      if ((this.tipoOperazione.idTipoOperazione === 3 || this.tipoOperazione.idTipoOperazione === 8) && this.dataPresentazione && this.fasiMonitoraggio[i].dtInizioPrevista && !this.isBR30
        && (!this.ngForm.form.get('dataInizioPrevista' + i).errors || !this.ngForm.form.get('dataInizioPrevista' + i).errors.length)) {
        if (this.sharedService.parseDate(this.fasiMonitoraggio[i].dtInizioPrevista) < this.sharedService.parseDate(this.dataPresentazione)
          && this.fasiMonitoraggio[i].idFaseMonit !== 30 && this.fasiMonitoraggio[i].idFaseMonit !== 31 && this.fasiMonitoraggio[i].idFaseMonit !== 32
          && this.fasiMonitoraggio[i].idFaseMonit !== 1 && this.fasiMonitoraggio[i].idFaseMonit !== 2 && this.fasiMonitoraggio[i].idFaseMonit !== 2) {
          this.ngForm.form.get('dataInizioPrevista' + i).setErrors({ error: 'minorePresentazione' });
          if (!this.isBR56) {
            if (!msg.find(m => m === "La data di inizio prevista e la data effettiva devono essere successive alla data di presentazione della domanda.")) {
              msg.push("La data di inizio prevista e la data effettiva devono essere successive alla data di presentazione della domanda.");
            }
          } else {
            if (!msg.find(m => m === "La data di inizio prevista e la data effettiva devono essere successive alla data di ammissibilità spese.")) {
              msg.push("La data di inizio prevista e la data effettiva devono essere successive alla data di ammissibilità spese.");
            }
          }
          this.hasValidationError = true;
        }
      }

    }

    this.setAllMarkAsTouched();
    if (this.hasValidationError) {
      this.showMessageError(msg);
    }
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

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA_AVVIO).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
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
    if (!this.loadedCodiceProgetto || !this.loadedProgrammazione ||
      !this.loadedTipoOperazione || !this.loadedFasiMonitoraggio || this.loadedSave
      || !this.loadedChiudiAttivita || !this.loadedRegolaBR30 || !this.loadedRegolaBR56) {
      return true;
    }
    return false;
  }

  annulla() {
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.resetMessageError();
    this.confermaSalvataggio = false;
    this.loadData();
  }

  isEmpty(obj) {
    for (var key in obj) {
      if (obj.hasOwnProperty(key))
        return false;
    }
    return true;
  }
}
