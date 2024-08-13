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
  selector: 'app-nuovo-modalita-pagamento-dialog',
  templateUrl: './nuovo-modalita-pagamento-dialog.component.html',
  styleUrls: ['./nuovo-modalita-pagamento-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoModalitaPagamentoDialogComponent implements OnInit {

  modalitaPagamento: string;
  codiceModalita: string;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  loaded: boolean = true;

  subscribers: any = {};

  constructor(private docModPagService: DocModPagService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<NuovoModalitaPagamentoDialogComponent>) { }

  ngOnInit(): void {

  }

  /* ----------------- Inizio gestione pulsanti ----------------- */
  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loaded = false;
    this.subscribers.inserisciModalitaPagamento = this.docModPagService.inserisciModalitaPagamento(this.data, this.modalitaPagamento, this.codiceModalita).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Modalità di pagamento aggiunta con successo.");
          this.dialogRef.close();
        } else {
          this.showMessageError("Errore in fase di aggiunta della modalità di pagamento.");
        }
      }
      this.loaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di aggiunta della modalità di pagamento.");
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
