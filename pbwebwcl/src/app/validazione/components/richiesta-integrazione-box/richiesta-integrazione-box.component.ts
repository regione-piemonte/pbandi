/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ValidazioneService } from '../../services/validazione.service';
import { IntegrazioniRevocaDTO } from 'src/app/gestione-integrazioni/commons/dto/integrazioniRevocaDTO';
import { GestioneProrogaComponent } from '../gestione-proroga/gestione-proroga.component';
import { ConfermaChiudiIntegrazioneComponent } from '../conferma-chiudi-integrazione/conferma-chiudi-integrazione.component';

@Component({
  selector: 'app-richiesta-integrazione-box',
  templateUrl: './richiesta-integrazione-box.component.html',
  styleUrls: ['./richiesta-integrazione-box.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RichiestaIntegrazioneBoxComponent implements OnInit, OnChanges {

  @Input("idProgetto") idProgetto: number;
  @Input("idDichiarazioneDiSpesa") idDichiarazioneDiSpesa: number;
  @Input("isVisible") isVisible: boolean = false;
  @Input("isFP") isFP: boolean = true;

  prorogheDisponibili;
  prorogaDaElaborare;
  prorogheDisponibiliStorico;
  storicoProroghe;
  notificaNumber;

  idStatoRichiesta: number;
  statoRichiesta: string
  //data invio
  dataInvio: Date;
  notifica: boolean;
  inviataDataSource: IntegrazioniRevocaDTO[];
  idIntregrazioneSpesa: number;
  stepProroga: any[];
  idIntegrazioneSpesa: any;
  dataNotifica: any;
  dataRichiesta: any;
  idStatoRichiestaInt: any;

  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();
  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("resetMessageSuccessError") resetMessageSuccessError = new EventEmitter<boolean>();
  @Output("setIdStatoRichiesta") setIdStatoRichiesta = new EventEmitter<number>();

  //LOADED
  loadedInizializzaInt: boolean = true;
  loadedProroghe: boolean = true;
  loadedStoricoProroghe: boolean = true;
  loadedStepProroga: boolean = true;
  loadedCerca: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private handleExceptionService: HandleExceptionService,
    private validazioneService: ValidazioneService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void { }

  once: boolean;
  ngOnChanges(changes: SimpleChanges): void {
    this.inizializzaIntegrazione();
  }

  initIntegrazione() {
    if ((this.isVisible || !this.isFP) && this.idDichiarazioneDiSpesa && !this.once) {
      this.once = true;
      this.inizializzaIntegrazione();
    }
  }

  inizializzaIntegrazione() {
    this.loadedInizializzaInt = false;
    this.validazioneService.initIntegrazione(this.idDichiarazioneDiSpesa.toString()).subscribe(data => {
      this.idIntegrazioneSpesa = data.idIntegrazioneSpesa;
      this.idStatoRichiesta = data?.idStatoRichiesta ? +data?.idStatoRichiesta : null;
      this.setIdStatoRichiesta.emit(this.idStatoRichiesta);
      this.statoRichiesta = data?.statoRichiesta?.toLowerCase();
      this.dataInvio = data?.dataInvio;
      this.dataNotifica = data?.dataNotifica
      this.dataRichiesta = data?.dataRichiesta
      this.loadedInizializzaInt = true;
      this.idStatoRichiestaInt = data?.idStatoRichiesta
      this.getInfoRendicontazioni();
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di inizializzazione dell'integrazione.");
      this.loadedInizializzaInt = true;
    });
  }


  getInfoRendicontazioni() {
    this.getInfoProrogheRendicontazione();
    this.getStepProroga();
    this.getInfoStoricoProrogheRendicontazione();
  }

  getInfoProrogheRendicontazione() {
    this.loadedProroghe = false;
    this.validazioneService.getProrogaIntegrazione(this.idDichiarazioneDiSpesa).subscribe(data => {
      this.prorogaDaElaborare = data;
      this.prorogheDisponibili = this.prorogaDaElaborare?.length > 0 ? true : false;
      this.notificaNumber = this.prorogaDaElaborare?.length;
      this.notifica = this.prorogaDaElaborare?.length > 0 ? true : false;
      this.loadedProroghe = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle proroghe.");
      this.loadedProroghe = true;
    });
  }

  getInfoStoricoProrogheRendicontazione() {
    this.loadedStoricoProroghe = false;
    this.validazioneService.getStoricoProrogheIntegrazione(this.idDichiarazioneDiSpesa).subscribe(data => {
      this.storicoProroghe = data;
      this.prorogheDisponibiliStorico = this.storicoProroghe.length > 0 ? true : false;
      this.loadedStoricoProroghe = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dello storico delle proroghe.");
      this.loadedStoricoProroghe = true;
    });
  }

  getStepProroga() {
    this.loadedStepProroga = false;
    this.validazioneService.getStepProroga(this.idDichiarazioneDiSpesa).subscribe(data => {
      this.stepProroga = data;
      this.loadedStepProroga = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dello step della proroga.");
      this.loadedStepProroga = true;
    });
  }

  gestioneProroga() {
    this.resetMessages();
    const dialogRef = this.dialog.open(GestioneProrogaComponent, {
      height: 'auto',
      width: 'auto',
      data: {
        stepProroga: this.stepProroga,
        storicoProroghe: this.storicoProroghe,
        prorogaDaElaborare: this.prorogaDaElaborare,
        idIntregrazioneSpesa: this.idIntregrazioneSpesa,
        idProgetto: this.idProgetto,
        idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.getInfoRendicontazioni();
    });
  }

  setDataNotifica() {
    this.resetMessages();
    this.dataNotifica = new Date(this.dataNotifica);
    let dataModifica = this.dataNotifica.getFullYear() + "/" + ("0" + (this.dataNotifica.getMonth() + 1)).slice(-2) + "/" + this.dataNotifica.getDate();
    this.subscribers.setDataNotifica = this.validazioneService.setDataNotifica(this.idIntegrazioneSpesa, dataModifica).subscribe(data => {
      if (data?.esito) {
        this.showMessageSuccess("Data notifica salvata con successo.");
        setTimeout(() => {
          this.getInfoRendicontazioni();
        }, 2000);
      } else {
        this.showMessageError("Errore in fase di salvataggio della data.");
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio della data.");
      this.loadedCerca = true;
    });
  }

  chiudiRichiestaIntegrazione() {
    this.resetMessages();
    const dialogRef = this.dialog.open(ConfermaChiudiIntegrazioneComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: this.idIntegrazioneSpesa,
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      if (dialogResult) {
        this.showMessageSuccess(dialogResult);
        this.inizializzaIntegrazione();
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess.emit(msg);
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  resetMessages() {
    this.resetMessageSuccessError.emit(true);
  }

  isLoading() {
    if (!this.loadedCerca || !this.loadedInizializzaInt || !this.loadedProroghe
      || !this.loadedStepProroga || !this.loadedStoricoProroghe) {
      return true;
    }
    return false;
  }



}
