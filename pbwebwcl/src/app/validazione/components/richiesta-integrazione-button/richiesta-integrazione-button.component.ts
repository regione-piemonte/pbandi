/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ValidazioneService } from '../../services/validazione.service';
import { RichiediIntegrazioneRequest } from '../../commons/requests/richiesta-integrazione-request';
import { RichiestaIntegrazioneDialogComponent } from '../richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';

const msgRichiestaIntegrazione: string = "ATTENZIONE! È presente una richiesta di integrazione.";

@Component({
  selector: 'app-richiesta-integrazione-button',
  templateUrl: './richiesta-integrazione-button.component.html',
  styleUrls: ['./richiesta-integrazione-button.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RichiestaIntegrazioneButtonComponent implements OnInit {

  @Input("idProgetto") idProgetto: number;
  @Input("idBandoLinea") idBandoLinea: number;
  @Input("idDichiarazioneDiSpesa") idDichiarazioneDiSpesa: number;
  @Input("isVisible") isVisible: boolean;
  @Input("isFP") isFP: boolean;
  @Input("isBR79") isBR79: boolean;
  @Input("idStatoRichiesta") idStatoRichiesta: number;
  @Input("nDocuSpesaSospesi") nDocuSpesaSospesi: number;
  @Input("richiestaIntegrazioneAbilitata") richiestaIntegrazioneAbilitata: boolean;
  @Input("esisteRichiestaIntegrazioneAperta") esisteRichiestaIntegrazioneAperta: boolean; //ora utilizzato solo in validazione per br79

  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();
  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("messageWarning") messageWarning = new EventEmitter<string>();
  @Output("resetMessageSuccessError") resetMessageSuccessError = new EventEmitter<boolean>();
  @Output("refresh") refresh = new EventEmitter<boolean>();

  //LOADED
  loadedCerca: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private handleExceptionService: HandleExceptionService,
    private validazioneService: ValidazioneService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void { }


  get isRichApertaBr79() {
    return this.esisteRichiestaIntegrazioneAperta && this.isBR79;
  }

  get tooltipRichApertaBr79() {
    return this.isRichApertaBr79 ? "Esiste già un richiesta integrazione" : null;
  }

  richiediIntegrazione() {
    this.resetMessages();
    let dialogRef = this.dialog.open(RichiestaIntegrazioneDialogComponent, {
      width: '60%',
      data: {
        nDocuSpesaSospesi: this.isVisible || !this.isFP ? this.nDocuSpesaSospesi : null,
        progBandoLineaIntervento: this.idBandoLinea,
        idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa,
        idProgetto: this.idProgetto
      }
    });
    dialogRef.afterClosed().subscribe(data => {

      if (data?.id == "old") {
        if (data.motivazione) {
          this.loadedCerca = false;
          this.subscribers.richiestaIntegrazione = this.validazioneService.richiediIntegrazione(new RichiediIntegrazioneRequest(this.idDichiarazioneDiSpesa, data.motivazione)).subscribe(data => {
            if (data) {
              if (data.esito) {
                this.showMessageSuccess(data.messaggio);
                this.showMessageWarning(msgRichiestaIntegrazione);
                this.refresh.emit(true);
              } else {
                this.showMessageError(data.messaggio);
              }
            }
            this.loadedCerca = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di richiesta integrazione.");
            this.loadedCerca = true;
          });
        }
      } else {
        this.refresh.emit(true);
      }
    });
  }


  showMessageSuccess(msg: string) {
    this.messageSuccess.emit(msg);
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  showMessageWarning(msg: string) {
    this.messageWarning.emit(msg);
  }

  resetMessages() {
    this.resetMessageSuccessError.emit(true);
  }

  isLoading() {
    if (!this.loadedCerca) {
      return true;
    }
    return false;
  }



}
