/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DocumentoAllegatoDTO, HandleExceptionService } from '@pbandi/common-lib';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';
import { AllegatiDocSpesaQuietanzeDTO } from '../../commons/dto/allegati-doc-spesa-quietanze-dto';
import { AllegatiDocSospesiQuietanzeDialogComponent } from '../allegati-doc-sospesi-quietanze-dialog/allegati-doc-sospesi-quietanze-dialog.component';

@Component({
  selector: 'app-conferma-invio-integrazione',
  templateUrl: './conferma-invio-integrazione.component.html',
  styleUrls: ['./conferma-invio-integrazione.component.scss']
})
export class ConfermaInvioIntegrazioneComponent implements OnInit {

  datiBackend = [];
  allegati;
  subscribers: any = {};
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  today: Date = new Date();
  tipoBottone: string;
  allegatiDocSpesaQuietanze: Array<AllegatiDocSpesaQuietanzeDTO>;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  isMessageErrorVisible: boolean;
  messageError: string;

  //LOADED
  loadedAllegati: boolean = true;
  loadedAllegatiDoc: boolean = true;
  loadedAggiorna: boolean = true;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    private integrazioniService: GestioneIntegrazioniService,
    public dialogRef: MatDialogRef<ConfermaInvioIntegrazioneComponent>,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.tipoBottone = this.data.dataKey2;
    if (this.tipoBottone === 'revoca') {
      this.loadedAllegati = false;
      this.subscribers.allegati = this.integrazioniService.getAllegatiIntegrazione(this.data.dataKey.idRichIntegrazione).subscribe(data => {
        if (data) {
          for (let a of data) {
            if (a.flagEntita === 'I') {
              this.letteraAccompagnatoria.push(Object.assign({}, a))
            } else {
              this.allegatiGenerici.push(Object.assign({}, a))
            }

          }
        } else {
          this.allegati = new Array<DocumentoAllegatoDTO>();
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli allegati.")
        this.loadedAllegati = true;
      });
    }

    else if (this.tipoBottone === 'controlli') {
      this.loadedAllegati = false;
      this.subscribers.allegati = this.integrazioniService.getAllegatiControlli(this.data.dataKey.idIntegrazione).subscribe(data => {
        if (data) {
          for (let a of data) {
            if (a.flagEntita === 'I') {
              this.letteraAccompagnatoria.push(Object.assign({}, a))
            } else {
              this.allegatiGenerici.push(Object.assign({}, a))
            }
          }
        } else {
          this.allegati = new Array<DocumentoAllegatoDTO>();
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli allegati.")
        this.loadedAllegati = true;
      });
    }

    else if (this.tipoBottone === 'rendicontazione') {
      this.loadedAllegati = false;
      this.subscribers.allegati = this.integrazioniService.getAllegatiNuovaIntegrazioneDS(this.data.dataKey.nDichiarazioneSpesa, this.data.dataKey.idIntegrazioneSpesa).subscribe(data => {
        if (data) {
          for (let a of data) {
            if (a.flagEntita === 'I') {
              this.letteraAccompagnatoria.push(Object.assign({}, a))
            } else {
              this.allegatiGenerici.push(Object.assign({}, a))
            }
          }
        } else {
          this.allegati = new Array<DocumentoAllegatoDTO>();
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli allegati.")
        this.loadedAllegati = true;
      });
      this.loadedAllegatiDoc = false;
      this.integrazioniService.getAllegatiDocSpesaQuietanzeDaInviare(this.data.dataKey.idIntegrazioneSpesa).subscribe(data => {
        this.allegatiDocSpesaQuietanze = data;
        this.loadedAllegatiDoc = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli allegati dei documenti sospesi.")
        this.loadedAllegatiDoc = true;
      });
    }
  }

  get isAllegatiDocSpesaQuietanze() {
    return this.allegatiDocSpesaQuietanze?.length > 0;
  }

  openAllegatiDocSospesiQuietanze() {
    this.dialog.open(AllegatiDocSospesiQuietanzeDialogComponent, {
      width: '60%',
      data: {
        allegati: this.allegatiDocSpesaQuietanze
      }
    })
  }

  invia() {
    if (this.tipoBottone === 'revoca') {
      this.loadedAggiorna = false;
      this.integrazioniService.aggiornaIntegrazioneRevoche(this.data.dataKey.idRichIntegrazione).subscribe(data => {
        if (data) {
          this.closeDialog(true);
        } else {
          this.showMessageError("Errore in fase di aggiornamento.");
        }
        this.loadedAggiorna = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di aggiornamento.");
        this.loadedAggiorna = true;
      });
    }

    else if (this.tipoBottone === 'controlli') {
      this.loadedAggiorna = false;
      this.integrazioniService.aggiornaControlli(this.data.dataKey.idIntegrazione).subscribe(data => {
        if (data) {
          this.closeDialog(true);
        } else {
          this.showMessageError("Errore in fase di aggiornamento.");
        }
        this.loadedAggiorna = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di aggiornamento.");
        this.loadedAggiorna = true;
      });
    }

    else if (this.tipoBottone === 'rendicontazione') {
      this.loadedAggiorna = false;
      this.integrazioniService.inviaIntegrazioneRendicontazione(this.data.dataKey.idIntegrazioneSpesa).subscribe(data => {
        if (data) {
          this.closeDialog(true);
        } else {
          this.showMessageError("Errore in fase di invio.");
        }
        this.loadedAggiorna = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di invio.");
        this.loadedAggiorna = true;
      });
    }

  }

  closeDialog(res?: boolean) {
    this.dialogRef.close(res);
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
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

  isLoading() {
    if (!this.loadedAllegati || !this.loadedAggiorna || !this.loadedAllegatiDoc) {
      return true;
    }
    return false;
  }

}
