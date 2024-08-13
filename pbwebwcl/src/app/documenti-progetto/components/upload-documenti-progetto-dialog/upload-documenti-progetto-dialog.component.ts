/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CodiceDescrizioneDTO } from 'src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto';

@Component({
  selector: 'app-upload-documenti-progetto-dialog',
  templateUrl: './upload-documenti-progetto-dialog.component.html',
  styleUrls: ['./upload-documenti-progetto-dialog.component.scss']
})
export class UploadDocumentiProgettoDialogComponent implements OnInit {

  codiceProgetto: string;
  dimMaxSingoloFile: number;
  estensioniConsentite: Array<string>;
  //categorieAnagrafica: Array<CodiceDescrizioneDTO>; //non utilizzato per adesso, uso sempre "OI"
  tipiDocumento: Array<CodiceDescrizioneDTO>;
  tipoDocumentoSelected: CodiceDescrizioneDTO;
  file: File;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<UploadDocumentiProgettoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.codiceProgetto = this.data.codiceProgetto;
    this.tipiDocumento = [this.data.tipoDocumento];
    this.tipoDocumentoSelected = this.tipiDocumento[0];
    this.dimMaxSingoloFile = this.dimMaxSingoloFile;
    this.estensioniConsentite = this.data.estensioniConsentite;
    // this.categorieAnagrafica = this.data.categorieAnagrafica;
  }

  handleFileInput(file: File) {
    this.file = file;
  }

  salva() {
    this.resetMessageError();
    if (this.file.size > this.dimMaxSingoloFile) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxSingoloFile);
      return;
    }
    let ok: boolean = false;
    for (let ext of this.estensioniConsentite) {
      if (this.file.name.endsWith("." + ext.trim())) {
        ok = true;
        break;
      }
    }
    if (!ok) {
      this.showMessageError("Tipo di file non consentito.");
      return;
    }
    this.dialogRef.close({ tipoDocumento: this.tipoDocumentoSelected, file: this.file });//categoriaAnag: this.categorieAnagrafica.find(c => c.descrizione === "OI")
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    this.dialogRef.close();
  }

}
