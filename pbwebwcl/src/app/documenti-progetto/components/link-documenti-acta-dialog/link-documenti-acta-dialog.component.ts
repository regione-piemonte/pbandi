/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DocumentiProgettoService } from '../../services/documenti-progetto.service';

@Component({
  selector: 'app-link-documenti-acta-dialog',
  templateUrl: './link-documenti-acta-dialog.component.html',
  styleUrls: ['./link-documenti-acta-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class LinkDocumentiActaDialogComponent implements OnInit {

  idProgetto: number;
  link: string;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedSalva: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<LinkDocumentiActaDialogComponent>,
    private documentiProgettoService: DocumentiProgettoService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
  }

  salva() {
    this.loadedSalva = false;
    this.subscribers.salva = this.documentiProgettoService.salvaDocumentoACTA(this.idProgetto, this.link).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.close("Salvataggio avvenuto con successo.");
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err),
        this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close(message?: string) {
    this.dialogRef.close(message);
  }

  isLoading() {
    if (!this.loadedSalva) {
      return true;
    }
    return false;
  }
}
