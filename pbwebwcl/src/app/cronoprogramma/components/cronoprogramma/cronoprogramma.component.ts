/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CronoprogrammaItem } from '../../commons/dto/cronoprogramma-item';
import { TipoOperazioneDTO } from '../../commons/dto/tipo-operazione-dto';
import { CronoprogrammaService } from '../../services/cronoprogramma.service';
import { SalvaFasiMonitoraggioGestioneRequest } from "../../commons/request/salva-fasi-monitoraggio-gestione";
import { Iter } from "../../commons/dto/iter";
import { DatiCronoprogramma } from "../../commons/dto/dati-cronoprogramma";
import { MotivoScostamento } from "../../commons/dto/motivo-scostamento";
import { ExecResults } from "../../commons/dto/exec-results";
import { DatePipe } from "@angular/common";
import { SharedService } from 'src/app/shared/services/shared.service';
import { KeyValue } from "../../commons/dto/key-value";
import { NgForm } from "@angular/forms";
import * as moment from 'moment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cronoprogramma',
  templateUrl: './cronoprogramma.component.html',
  styleUrls: ['./cronoprogramma.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CronoprogrammaComponent implements OnInit {

  @Input('idProgetto') idProgetto: number;
  @Input('user') user: UserInfoSec;
  @Input('regola30attiva') regola30attiva: boolean;
  @Input('isBR56') isBR56: boolean;
  @Input('cronoprogrammaObbligatorio') cronoprogrammaObbligatorio: boolean;
  isConfermatoSuccess: boolean;

  programmazione: string;
  tipoOperazione: TipoOperazioneDTO;
  iter: Iter;

  fasiMonitoraggio: Array<CronoprogrammaItem>;
  datiCrono: DatiCronoprogramma;
  fasiMonitoraggio1: Array<CronoprogrammaItem>;
  fasiMonitoraggioNew: Array<CronoprogrammaItem> = new Array<CronoprogrammaItem>();
  motiviScostamento: Array<MotivoScostamento>;
  dataConcessione: Date;
  dataPresentazione: Date;
  execResults: ExecResults = { resultCode: "", fldErrors: new Array<KeyValue>(), globalErrors: new Array<string>(), globalMessages: new Array<string>(), model: "" };

  displayedColumns: string[] = ['fase', 'dataInizioPrevista', 'dataInizioEffettiva', 'dataFinePrevista', 'dataFineEffettiva', 'motivo'];
  dataSource: MatTableDataSource<CronoprogrammaItem> = new MatTableDataSource<CronoprogrammaItem>();

  @ViewChild("cronoForm", { static: true }) cronoForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedProgrammazione: boolean = true;
  loadedTipoOperazione: boolean = true;
  loadedFasiMonitoraggio: boolean = true;
  loadedIter: boolean = true;
  loadedSave: boolean = false;
  loadedDtConcessione: boolean = true;
  loadedDtPresentazioneDomanda: boolean = true;
  loadedMotivoScostamento: boolean = true;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  dateFormat: any = null;
  confermaSalvataggio: boolean = false;

  errMsgs: string[] = [""];


  // dtInizioEffettiva: FormControl = new FormControl();

  constructor(
    private router: Router,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private cronoprogrammaService: CronoprogrammaService,
    private sharedService: SharedService,
    private datePipe: DatePipe,
    private changeDetectorRefs: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
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

        //fasi Monitoraggio Gestione
        this.loadedFasiMonitoraggio = false;
        this.subscribers.codice = this.cronoprogrammaService.getFasiMonitoraggioGestione(this.idProgetto, this.programmazione).subscribe(data6 => {
          if (data6) {
            this.fasiMonitoraggio = data6.item;
            this.datiCrono = data6.datiCrono;

            this.dataSource = new MatTableDataSource<CronoprogrammaItem>();
            this.dataSource.data = this.fasiMonitoraggio;
            this.changeDetectorRefs.detectChanges();
          }
          this.loadedFasiMonitoraggio = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedFasiMonitoraggio = true;
        });

        //motivi scostamento
        this.loadedMotivoScostamento = false;
        this.subscribers.codice = this.cronoprogrammaService.getMotiviScostamento().subscribe(data7 => {
          if (data7) {
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

  getErrorMessage(pickerInput: string): string {
    if (!pickerInput || pickerInput === '') {
      return 'Please choose a date.';
    }
    return this.isMyDateFormat(pickerInput);
  }

  isMyDateFormat(date: string): string {
    if (moment(date, ['DD/MM/YYYY']).isValid()) {
      return 'data non valida: inserire una data nel formato gg/mm/aaaa';
    } else {

    }
    return 'Unknown error.';
  }


  refresh() {
    //fasiMonitoraggioGestione
    this.loadedFasiMonitoraggio = false;
    this.subscribers.codice = this.cronoprogrammaService.getFasiMonitoraggioGestione(this.idProgetto, this.programmazione).subscribe(data6 => {
      if (data6) {
        this.fasiMonitoraggio = data6.item;
        this.dataSource = new MatTableDataSource<CronoprogrammaItem>();
        this.dataSource.data = this.fasiMonitoraggio;
        this.changeDetectorRefs.detectChanges();
        setTimeout(() => {
          if (this.cronoprogrammaObbligatorio || (!this.cronoprogrammaObbligatorio && this.isTableCronoprogrammaCompilata())) {
            this.controllaDatiPerChiusura();
          }
        }, 1);
      }
      this.loadedFasiMonitoraggio = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedFasiMonitoraggio = true;
    });
  }

  controllaDatiPerChiusura() {
    return this.controllaDati(true);
  }

  salva(fromParent?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    for (let item of this.fasiMonitoraggio) {
      if (typeof item.dtInizioPrevista !== 'string') {
        item.dtInizioPrevista = this.sharedService.formatDateToString(item.dtInizioPrevista);
      }
      if (typeof item.dtFinePrevista !== 'string') {
        item.dtFinePrevista = this.sharedService.formatDateToString(item.dtFinePrevista);
      }
      if (typeof item.dtInizioEffettiva !== 'string') {
        item.dtInizioEffettiva = this.sharedService.formatDateToString(item.dtInizioEffettiva);
      }
      if (typeof item.dtFineEffettiva !== 'string') {
        item.dtFineEffettiva = this.sharedService.formatDateToString(item.dtFineEffettiva);
      }
    }
    this.fasiMonitoraggio.map(r => {
      r.dtInizioPrevista = r.dtInizioPrevista && r.dtInizioPrevista.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtInizioPrevista), 'yyyy-MM-dd') : "";
      r.dtFinePrevista = r.dtFinePrevista && r.dtFinePrevista.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtFinePrevista), 'yyyy-MM-dd') : "";
      r.dtInizioEffettiva = r.dtInizioEffettiva && r.dtInizioEffettiva.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtInizioEffettiva), 'yyyy-MM-dd') : "";
      r.dtFineEffettiva = r.dtFineEffettiva && r.dtFineEffettiva.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtFineEffettiva), 'yyyy-MM-dd') : "";
      return r;
    });
    this.changeDetectorRefs.detectChanges();
    if ((!fromParent && !this.controllaDati()) || fromParent) {
      this.isConfermatoSuccess = false;
      this.resetMessageSuccess();
      this.resetMessageError();
      this.resetMessageWarning();
      this.fasiMonitoraggioNew = new Array<CronoprogrammaItem>();
      this.fasiMonitoraggio.forEach(val => this.fasiMonitoraggioNew.push(Object.assign({}, val)));
      this.fasiMonitoraggioNew.map(r => {
        r.dtInizioPrevista = r.dtInizioPrevista && r.dtInizioPrevista.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtInizioPrevista), 'dd/MM/yyyy') : "";
        r.dtFinePrevista = r.dtFinePrevista && r.dtFinePrevista.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtFinePrevista), 'dd/MM/yyyy') : "";
        r.dtInizioEffettiva = r.dtInizioEffettiva && r.dtInizioEffettiva.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtInizioEffettiva), 'dd/MM/yyyy') : "";
        r.dtFineEffettiva = r.dtFineEffettiva && r.dtFineEffettiva.length ? this.datePipe.transform(this.sharedService.parseDate(r.dtFineEffettiva), 'dd/MM/yyyy') : "";
        return r;
      });
      if (fromParent) {
        this.confermaSalva(true);
      } else {
        this.showMessageWarning("Confermare il salvataggio dei dati.");
        this.confermaSalvataggio = true;
      }
      //spostato la validazione di questo servizio dentro this.controllaDati()
      //this.cronoprogrammaService.validateDatiCronoProgramma()
    }
  }

  confermaSalva(fromParent?: boolean) {
    this.loadedSave = true;
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.subscribers.save = this.cronoprogrammaService.salvaFasiMonitoraggioGestione(new SalvaFasiMonitoraggioGestioneRequest(this.idProgetto, this.iter.idIter, this.fasiMonitoraggioNew))
      .subscribe(data => {
        if (data) {
          if (data.successo) {
            if (fromParent) {
              this.isConfermatoSuccess = true;
            } else {
              this.showMessageSuccess(data.messaggi[0]);
              this.confermaSalvataggio = false;
              this.isMessageWarningVisible = false;
              this.refresh();
              this.showMessageSuccess(data.messaggi[0]);
            }
          } else {
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
        this.isMessageWarningVisible = false;
        this.showMessageError(["Errore nel salvataggio della cronoprgramma."]);
        this.loadedSave = false;
      });

  }

  error: Array<string> = new Array<string>();
  eseguiControlliFormaliSuRiga(item: CronoprogrammaItem, index: number) {
    let isError: boolean;
    let isDtInizioPrevistaValid: boolean = true;
    let isDtInizioEffettivaValid: boolean = true;
    let isDtFineEffettivaValid: boolean = true;
    let isDtFinePrevistaValid: boolean = true;

    if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0) {
      if (!this.sharedService.parseDate(item.dtInizioPrevista)) {
        this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'format' });
        this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
        isDtInizioPrevistaValid = false;
        isError = true;
      }
    }
    if (this.datiCrono.codiceFaseFinaleObbligatoria && item.codIgrue
      && this.datiCrono.codiceFaseFinaleObbligatoria.toLowerCase() === item.codIgrue.toLowerCase()
      && (item.dtInizioEffettiva === null || item.dtInizioEffettiva.length === 0)
      && item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0) {
      this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'dataInFin' });
      this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
      isDtInizioEffettivaValid = false;
      isError = true;
    }
    if (this.datiCrono.codiceFaseFinaleObbligatoria && item.codIgrue
      && this.datiCrono.codiceFaseFinaleObbligatoria.toLowerCase() === item.codIgrue.toLowerCase()
      && (item.dtFineEffettiva === null || item.dtFineEffettiva.length === 0)
      && item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0) {
      this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'dataInFin' });
      this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
      isDtFineEffettivaValid = false;
      isError = true;
    }
    if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0) {
      if (!this.sharedService.parseDate(item.dtInizioEffettiva)) {
        this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'format' });
        this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
        isDtInizioEffettivaValid = false;
        isError = true;
      }
    }
    if (item.dtFinePrevista !== null && item.dtFinePrevista.length > 0) {
      if (!this.sharedService.parseDate(item.dtFinePrevista)) {
        this.cronoForm.form.get('dataFinePrevista' + index).setErrors({ error: 'format' });
        this.cronoForm.form.get('dataFinePrevista' + index).markAsTouched();
        isDtFinePrevistaValid = false;
        isError = true;
      }
    }
    if (item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0) {
      if (!this.sharedService.parseDate(item.dtFineEffettiva)) {
        this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'format' });
        this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
        isDtFineEffettivaValid = false;
        isError = true;
      }
    }
    // 3b. Fase con "data inizio effettiva" valorizzata e
    // "data inizio prevista" non valorizzata
    if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0 && isDtInizioEffettivaValid
      && (item.dtInizioPrevista === null || item.dtInizioPrevista.length === 0)) {
      this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'dataIn' });
      this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
      isError = true;
    }
    // 3c. Fase con "data fine effettiva" valorizzata e "data fine prevista"
    // non valorizzata
    if (item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0 && isDtFineEffettivaValid
      && (item.dtFinePrevista === null || item.dtFinePrevista.length === 0)) {
      this.cronoForm.form.get('dataFinePrevista' + index).setErrors({ error: 'dataIn' });
      this.cronoForm.form.get('dataFinePrevista' + index).markAsTouched();
      isError = true;
    }
    // 3e. Fase con "data inizio/fine prevista" parzialmente valorizzate
    if ((item.dtInizioPrevista === null || item.dtInizioPrevista.length === 0)
      && item.dtFinePrevista !== null && item.dtFinePrevista.length > 0 && isDtFinePrevistaValid) {
      this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'required' });
      this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
      if (!this.error.find(e => e === "Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.")) {
        this.error.push("Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.");
      }
      isError = true;
    }
    if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0 && isDtInizioPrevistaValid
      && (item.dtFinePrevista === null || item.dtFinePrevista.length === 0)) {
      this.cronoForm.form.get('dataFinePrevista' + index).setErrors({ error: 'required' });
      this.cronoForm.form.get('dataFinePrevista' + index).markAsTouched();
      if (!this.error.find(e => e === "Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.")) {
        this.error.push("Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.");
      }
      isError = true;
    }
    // 3f. L'attore inserisce Data fine prevista minore di data inizio
    // prevista
    if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0 && isDtInizioPrevistaValid
      && item.dtFinePrevista !== null && item.dtFinePrevista.length > 0 && isDtFinePrevistaValid) {
      if (this.sharedService.parseDate(item.dtFinePrevista) < this.sharedService.parseDate(item.dtInizioPrevista)) {
        this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'incoerente' });
        this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
        this.cronoForm.form.get('dataFinePrevista' + index).setErrors({ error: 'incoerente' });
        this.cronoForm.form.get('dataFinePrevista' + index).markAsTouched();
        if (!this.error.find(e => e === "Una data fine prevista deve essere uguale o successiva alla data di inizio prevista.")) {
          this.error.push("Una data fine prevista deve essere uguale o successiva alla data di inizio prevista.");
        }
        isError = true;
      }
    }
    // 3g. L'attore inserisce Data fine effettiva minore di data inizio
    // effettiva
    if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0 && isDtInizioEffettivaValid
      && item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0 && isDtFineEffettivaValid) {
      if (this.sharedService.parseDate(item.dtFineEffettiva) < this.sharedService.parseDate(item.dtInizioEffettiva)) {
        this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'incoerente' });
        this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
        this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'incoerente' });
        this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
        if (!this.error.find(e => e === "Una data fine effettiva deve essere uguale o successiva alla data di inizio effettiva.")) {
          this.error.push("Una data fine effettiva deve essere uguale o successiva alla data di inizio effettiva.");
        }
        isError = true;
      }
    }
    // 3h. L'attore inserisce Data fine effettiva e/o data inizio effettiva
    // future
    let oggi: Date = new Date();
    oggi.setHours(0, 0, 0, 0);
    if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0 && isDtInizioEffettivaValid) {
      if (this.sharedService.parseDate(item.dtInizioEffettiva) > oggi) {
        this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'maggioreOggi' });
        this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
        if (!this.error.find(e => e === "La data di inizio effettiva e la data di fine effettiva non possono essere successive alla data odierna.")) {
          this.error.push("La data di inizio effettiva e la data di fine effettiva non possono essere successive alla data odierna.");
        }
        isError = true;
      }
    }
    if (item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0 && isDtFineEffettivaValid) {
      if (this.sharedService.parseDate(item.dtFineEffettiva) > oggi) {
        this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'maggioreOggi' });
        this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
        if (!this.error.find(e => e === "La data di inizio effettiva e la data di fine effettiva non possono essere successive alla data odierna.")) {
          this.error.push("La data di inizio effettiva e la data di fine effettiva non possono essere successive alla data odierna.");
        }
        isError = true;
      }
    }
    // 3i. L'attore inserisce Data inizio previsa e/o data inizio effettiva
    // per la prima fase di un iter precedente alla data di presentazione
    // domanda
    // [DM] 15-11-2010: vale per tutte le fasi

    if (this.sharedService.parseDate(this.dataPresentazione)) {
      let idTipoOperazione: number = this.tipoOperazione.idTipoOperazione;
      if ((idTipoOperazione === 3 || idTipoOperazione === 8) && !this.regola30attiva) {
        if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0 && isDtInizioPrevistaValid) {
          if (this.sharedService.parseDate(item.dtInizioPrevista) < this.sharedService.parseDate(this.dataPresentazione)
            && item.idFaseMonit !== 30 && item.idFaseMonit !== 31 && item.idFaseMonit !== 32
            && item.idFaseMonit !== 1 && item.idFaseMonit !== 2 && item.idFaseMonit !== 3) {
            this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'minorePres' });
            this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
            if (!this.isBR56) {
              if (!this.error.find(e => e === "La data prevista deve essere successiva alla data di presentazione della domanda.")) {
                this.error.push("La data prevista deve essere successiva alla data di presentazione della domanda.");
              }
            } else {
              if (!this.error.find(e => e === "La data prevista deve essere successiva alla data di ammissibilità spese.")) {
                this.error.push("La data prevista deve essere successiva alla data di ammissibilità spese.");
              }
            }
            isError = true;
          }
        }

        if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0 && isDtInizioEffettivaValid) {
          if (this.sharedService.parseDate(item.dtInizioEffettiva) < this.sharedService.parseDate(this.dataPresentazione)
            && item.idFaseMonit !== 30 && item.idFaseMonit !== 31 && item.idFaseMonit !== 32
            && item.idFaseMonit !== 1 && item.idFaseMonit !== 2 && item.idFaseMonit !== 3) {
            this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'minorePres' });
            this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
            if (!this.isBR56) {
              if (!this.error.find(e => e === "La data effettiva deve essere successiva alla data di presentazione della domanda.")) {
                this.error.push("La data effettiva deve essere successiva alla data di presentazione della domanda.");
              }
            } else {
              if (!this.error.find(e => e === "La data effettiva deve essere successiva alla data di ammissibilità spese.")) {
                this.error.push("La data effettiva deve essere successiva alla data di ammissibilità spese.");
              }
            }
            isError = true;
          }
        }
      }
    }
    // spostato qua da controlli formali in modo che sia sempre eseguito
    if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0
      && item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0) {
      if (!this.sharedService.isDateEqual(this.sharedService.parseDate(item.dtInizioPrevista), this.sharedService.parseDate(item.dtInizioEffettiva))) {
        if (item.idMotivoScostamento === null) {
          console.log(this.sharedService.parseDate(item.dtInizioPrevista));
          console.log(this.sharedService.parseDate(item.dtInizioEffettiva));
          this.cronoForm.form.get('motivoScostamento' + index).setErrors({ error: 'required' });
          this.cronoForm.form.get('motivoScostamento' + index).markAsTouched();
          if (!this.error.find(e => e === "Se le date di inizio/fine effettiva sono sfasate rispetto alle date di inizio/fine prevista è obbligatorio selezionare un motivo di scostamento.")) {
            this.error.push("Se le date di inizio/fine effettiva sono sfasate rispetto alle date di inizio/fine prevista è obbligatorio selezionare un motivo di scostamento.");
          }
          isError = true;
        }
      }
    }
    if (item.dtFinePrevista !== null && item.dtFinePrevista.length > 0
      && item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0) {
      if (!this.sharedService.isDateEqual(this.sharedService.parseDate(item.dtFinePrevista), this.sharedService.parseDate(item.dtFineEffettiva))) {
        if (item.idMotivoScostamento === null) {
          this.cronoForm.form.get('motivoScostamento' + index).setErrors({ error: 'required' });
          this.cronoForm.form.get('motivoScostamento' + index).markAsTouched();
          if (!this.error.find(e => e === "Se le date di inizio/fine effettiva sono sfasate rispetto alle date di inizio/fine prevista è obbligatorio selezionare un motivo di scostamento.")) {
            this.error.push("Se le date di inizio/fine effettiva sono sfasate rispetto alle date di inizio/fine prevista è obbligatorio selezionare un motivo di scostamento.");
          }
          isError = true;
        }
      }
    }
    return isError;
  }

  eseguiControlliSostanzialiSuRiga(item: CronoprogrammaItem, index: number) {
    let isError: boolean;
    // Algoritmo 2.6.1 passo 1 e 2 unificati: verifica per le fasi che hanno
    // "data inizio prevista" e "data fine prevista" non null,
    // che "data inizio effettiva" e "data fine effettiva" siano non null (
    if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0
      && (item.dtInizioEffettiva === null || item.dtInizioEffettiva.length === 0)) {
      this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'required' });
      this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
      isError = true;
    }
    if (item.dtFinePrevista !== null && item.dtFinePrevista.length > 0
      && (item.dtFineEffettiva === null || item.dtFineEffettiva.length === 0)) {
      this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'required' });
      this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
      isError = true;
    }
    // jira 1800
    if (item.flagObbligatorio) {
      if (item.dtInizioPrevista === null || item.dtInizioPrevista.length === 0) {
        this.cronoForm.form.get('dataInizioPrevista' + index).setErrors({ error: 'required' });
        this.cronoForm.form.get('dataInizioPrevista' + index).markAsTouched();
        isError = true;
      }
      if (item.dtFinePrevista === null || item.dtFinePrevista.length === 0) {
        this.cronoForm.form.get('dataFinePrevista' + index).setErrors({ error: 'required' });
        this.cronoForm.form.get('dataFinePrevista' + index).markAsTouched();
        isError = true;
      }
      if (item.dtInizioEffettiva === null || item.dtInizioEffettiva.length === 0) {
        this.cronoForm.form.get('dtInizioEffettiva' + index).setErrors({ error: 'required' });
        this.cronoForm.form.get('dtInizioEffettiva' + index).markAsTouched();
        isError = true;
      }
      if (item.dtFineEffettiva === null || item.dtFineEffettiva.length === 0) {
        this.cronoForm.form.get('dtFineEffettiva' + index).setErrors({ error: 'required' });
        this.cronoForm.form.get('dtFineEffettiva' + index).markAsTouched();
        isError = true;
      }
    }

    return isError;
  }

  eseguiControlliFaseFinale() {
    let okFaseFinale: boolean = false;
    for (let item of this.fasiMonitoraggio) {
      if (this.datiCrono.codiceFaseFinaleObbligatoria && item.codIgrue
        && this.datiCrono.codiceFaseFinaleObbligatoria.toLowerCase() === item.codIgrue.toLowerCase()) {
        if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0
          && item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0) {
          if (this.sharedService.parseDate(item.dtInizioEffettiva) && this.sharedService.parseDate(item.dtFineEffettiva)) {
            okFaseFinale = true;
            break;
          }
        }
      }
    }
    return okFaseFinale;
  }

  eseguiControlliAltreFasi() {
    let altreFasiOk: boolean = true;
    let i: number = 0;
    for (let item of this.fasiMonitoraggio) {
      if (this.datiCrono.codiceFaseFinaleObbligatoria && item.codIgrue
        && this.datiCrono.codiceFaseFinaleObbligatoria.toLowerCase() === item.codIgrue.toLowerCase()) {
        if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0) {
          altreFasiOk = true;
          if (item.dtInizioEffettiva === null || item.dtInizioEffettiva.length === 0) {
            altreFasiOk = false;
            this.cronoForm.form.get('dtInizioEffettiva' + i).setErrors({ error: 'required' });
            this.cronoForm.form.get('dtInizioEffettiva' + i).markAsTouched();
          }
          if (item.dtFineEffettiva === null || item.dtFineEffettiva.length === 0) {
            altreFasiOk = false;
            this.cronoForm.form.get('dtFineEffettiva' + i).setErrors({ error: 'required' });
            this.cronoForm.form.get('dtFineEffettiva' + i).markAsTouched();
          }
          if (item.dtFinePrevista === null || item.dtFinePrevista.length === 0) {
            altreFasiOk = false;
            this.cronoForm.form.get('dataFinePrevista' + i).setErrors({ error: 'required' });
            this.cronoForm.form.get('dataFinePrevista' + i).markAsTouched();
          }
        }
      }
      i++;
    }
    return altreFasiOk;
  }

  controllaDati(chiusura?: boolean) {
    this.resetMessageError();
    this.error = [];
    let isError: boolean;
    let faseFinaleOk: boolean;
    let altreFasiOk: boolean;
    let i: number = 0;
    for (let item of this.fasiMonitoraggio) {
      let isRowError: boolean = this.eseguiControlliFormaliSuRiga(item, i);
      if (!isRowError && chiusura) {
        // proseguo con i controlli sostanziali solo se non ci sono
        // errori formali (date non valide o campi mancanti),
        // perché tali controlli avrebbero ottima probabilità di non
        // venire eseguiti o di fallire
        isRowError = this.eseguiControlliSostanzialiSuRiga(item, i);
      }
      if (isRowError) {
        isError = true;
      }
      i++;
    }
    if (!isError) {
      faseFinaleOk = this.eseguiControlliFaseFinale();
      if (faseFinaleOk) {
        altreFasiOk = this.eseguiControlliAltreFasi();
        // Attenzione! Si sono valorizzate le date della fase conclusiva: è necessario valorizzare le date delle altre fasi presenti
        if (!altreFasiOk)
          isError = true;
      }
    }

    if (this.error && this.error.length > 0) {
      this.showMessageError(this.error);
    }
    this.setAllMarkAsTouched();
    return isError;
  }

  setAllMarkAsTouched() {
    Object.keys(this.cronoForm.form.controls).forEach(key => {
      if (this.cronoForm.form.controls[key]) {
        this.cronoForm.form.controls[key].markAsTouched();
      }
    });
  }

  isTableCronoprogrammaCompilata() {
    let isRigaCompilata: boolean = false;
    let i: number = 0;
    for (let item of this.fasiMonitoraggio) {
      if (!this.cronoForm.form.get('dataInizioPrevista' + i) || !this.cronoForm.form.get('dataInizioPrevista' + i).disabled) {
        if (item.dtInizioPrevista !== null && item.dtInizioPrevista.length > 0) {
          isRigaCompilata = true;
          break;
        }
      }
      if (!this.cronoForm.form.get('dtInizioEffettiva' + i) || !this.cronoForm.form.get('dtInizioEffettiva' + i).disabled) {
        if (item.dtInizioEffettiva !== null && item.dtInizioEffettiva.length > 0) {
          isRigaCompilata = true;
          break;
        }
      }
      if (!this.cronoForm.form.get('dataFinePrevista' + i) || !this.cronoForm.form.get('dataFinePrevista' + i).disabled) {
        if (item.dtFinePrevista !== null && item.dtFinePrevista.length > 0) {
          isRigaCompilata = true;
          break;
        }
      }
      if (!this.cronoForm.form.get('dtFineEffettiva' + i) || !this.cronoForm.form.get('dtFineEffettiva' + i).disabled) {
        if (item.dtFineEffettiva !== null && item.dtFineEffettiva.length > 0) {
          isRigaCompilata = true;
          break;
        }
      }
      i++;
    }
    return isRigaCompilata;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA).subscribe(data => {
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
    const element = document.querySelector('#scrollCrono');
    element.scrollIntoView();
  }
  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollCrono');
    element.scrollIntoView();
  }
  showMessageError(msgs: Array<string>) {
    this.messageError = msgs;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollCrono');
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
    if (!this.loadedProgrammazione ||
      !this.loadedTipoOperazione || !this.loadedFasiMonitoraggio || this.loadedSave
      || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }

  annulla() {
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.resetMessageError();
    this.confermaSalvataggio = false;
  }

  isEmpty(obj) {
    for (var key in obj) {
      if (obj.hasOwnProperty(key))
        return false;
    }
    return true;
  }

  /*  toFormattedDate(iso: string) {
      const date = new Date(iso);
      console.log(date);
      return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
    }*/
}
