/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DocModPagService } from '../../services/doc-mod-pag.service';

@Component({
  selector: 'app-nuovo-tipo-documento-dialog',
  templateUrl: './nuovo-tipo-documento-dialog.component.html',
  styleUrls: ['./nuovo-tipo-documento-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoTipoDocumentoDialogComponent implements OnInit {

  tipoDocumento: string;
  codiceTipo: string;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  loaded: boolean = true;

  subscribers: any = {};

  constructor(private docModPagService: DocModPagService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<NuovoTipoDocumentoDialogComponent>) { }

  ngOnInit(): void {

  }

  /* ----------------- Inizio gestione pulsanti ----------------- */
  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loaded = false;
    this.subscribers.inserisci = this.docModPagService.inserisciTipoDocumentoSpesa(this.data, this.tipoDocumento, this.codiceTipo).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tipo di documento aggiunto con successo.");
          this.dialogRef.close();
        } else {
          this.showMessageError("Errore in fase di aggiunta del tipo di documento.");
        }
      }
      this.loaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di aggiunta del tipo di documento.");
      this.loaded = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    this.dialogRef.close();
  }
  /* ----------------- Fine gestione pulsanti ----------------- */
  isLoading() {
    if (!this.loaded) {
      return true;
    }
    return false;
  }
}
