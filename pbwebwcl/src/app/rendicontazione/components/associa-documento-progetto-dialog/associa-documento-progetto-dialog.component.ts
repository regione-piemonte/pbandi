/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { FormAssociaDocSpesa } from '../../commons/dto/form-associa-doc-spesa';
import { DocumentoDiSpesaService } from '../../services/documento-di-spesa.service';

@Component({
  selector: 'app-associa-documento-progetto-dialog',
  templateUrl: './associa-documento-progetto-dialog.component.html',
  styleUrls: ['./associa-documento-progetto-dialog.component.scss']
})
export class AssociaDocumentoProgettoDialogComponent implements OnInit {

  idDocumentoDiSpesa: number;
  idProgetto: number;
  idProgettoDocumento: number;
  form: FormAssociaDocSpesa;
  task: string;
  rendicontabile: number;
  rendicontabileFormatted: string;
  rendicontabileError: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedForm: boolean;
  loadedAssocia: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<AssociaDocumentoProgettoDialogComponent>,
    private documentoDiSpesaService: DocumentoDiSpesaService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idDocumentoDiSpesa = this.data.idDocumentoDiSpesa;
    this.idProgetto = this.data.idProgetto;
    this.idProgettoDocumento = this.data.idProgettoDocumento;
    this.loadedForm = false;
    this.subscribers.form = this.documentoDiSpesaService.popolaFormAssociaDocSpesa(this.idDocumentoDiSpesa).subscribe(data => {
      if (data) {
        this.form = data;
        this.rendicontabile = this.form.importoRendicontabile;
        if (this.rendicontabile) {
          this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
        }
        this.task = this.form.task;
      }
      this.loadedForm = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  setRendicontabile() {
    this.rendicontabile = this.sharedService.getNumberFromFormattedString(this.rendicontabileFormatted);
    if (this.rendicontabile !== null) {
      this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
    }
    if (this.rendicontabile > this.form.massimoRendicontabile) {
      this.rendicontabileError = true;
      this.showMessageError("Il totale del rendicontabile associato ai progetti è maggiore dell'importo massimo rendicontabile del documento di spesa.");
    } else {
      this.rendicontabileError = false;
      this.resetMessageError();
    }
  }

  associa() {
    this.loadedAssocia = false;
    this.subscribers.form = this.documentoDiSpesaService.associaDocumentoDiSpesaAProgetto(this.idDocumentoDiSpesa, this.idProgetto, this.idProgettoDocumento, this.task, this.rendicontabile).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.dialogRef.close(data.messaggi ? data.messaggi[0] : null);
        } else {
          this.showMessageError(data.messaggi ? data.messaggi[0] : "Errore nell'associazione del documento al progetto.");
        }
      }
      this.loadedAssocia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nell'associazione del documento al progetto.");
      this.loadedAssocia = true;
    });
  }

  close() {
    this.dialogRef.close();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedForm || !this.loadedAssocia) {
      return true;
    }
    return false;
  }

}
