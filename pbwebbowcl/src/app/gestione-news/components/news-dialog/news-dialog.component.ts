/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AvvisoDTO } from '../../commons/dto/avviso-dto';
import { CodiceDescrizioneDTO } from '../../commons/dto/codice-descrizione-dto';

@Component({
  selector: 'app-news-dialog',
  templateUrl: './news-dialog.component.html',
  styleUrls: ['./news-dialog.component.scss']
})
export class NewsDialogComponent implements OnInit {

  tipiFormControl = new FormControl();
  tipiAnagrafica: Array<CodiceDescrizioneDTO>;
  tutti = new CodiceDescrizioneDTO(0, 'TUTTI', 'TUTTI');
  type: string;
  destinatariDisabled: boolean;
  dataInizio = new FormControl();
  dataFine = new FormControl();
  hasValidationError: boolean;

  avviso: AvvisoDTO = new AvvisoDTO();

  @ViewChild('newsForm', { static: true }) newsForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<NewsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.tipiAnagrafica = this.data.tipiAnagrafica;
    this.type = this.data.type;
    if (this.data.avviso) {
      this.avviso = this.data.avviso;
      if (this.avviso.tipiAnagrafica.length === this.tipiAnagrafica.length) {
        this.tipiFormControl.setValue([this.tutti]);
      } else {
        let ids = this.avviso.tipiAnagrafica.map(t => t.id);
        let tipi = this.tipiAnagrafica.filter(t => ids.includes(t.id));
        this.tipiFormControl.setValue(tipi);
      }
      this.changeDestinatari();
      this.dataInizio.setValue(new Date(this.avviso.dtInizio));
      if (this.avviso.dtFine) {
        this.dataFine.setValue(new Date(this.avviso.dtFine));
      }
    }
  }

  changeDestinatari() {
    if (this.tipiFormControl && this.tipiFormControl.value && this.tipiFormControl.value.length > 0) {
      if (this.tipiFormControl.value.find(t => t.id === 0)) {
        this.tipiFormControl.setValue(this.tipiFormControl.value.filter(t => t.id === 0));
        this.destinatariDisabled = true;
        return;
      }
    }
    this.destinatariDisabled = false;
  }

  salva() {
    this.resetMessageError();
    this.checkRequiredForm(this.newsForm, 'titolo');
    this.checkRequiredForm(this.newsForm, 'contenuto');
    this.checkRequiredForm(this.newsForm, 'tipo');
    if (!this.tipiFormControl || !this.tipiFormControl.value || !this.tipiFormControl.value.length) {
      this.tipiFormControl.setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    if (!this.dataInizio || !this.dataInizio.value) {
      this.dataInizio.setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    this.newsForm.form.markAllAsTouched();
    this.tipiFormControl.markAsTouched();
    this.dataInizio.markAsTouched();

    if (this.hasValidationError) {
      this.showMessageError("Inserire tutti i campi obbligatori.");
      return;
    }
    this.avviso.dtInizio = new Date(this.dataInizio.value);
    if (this.dataFine && this.dataFine.value) {
      this.avviso.dtFine = new Date(this.dataFine.value);
    } else {
      this.avviso.dtFine = null;
    }
    if (this.tipiFormControl.value.find(t => t.id === 0)) {
      this.avviso.tipiAnagrafica = this.tipiAnagrafica;
    } else {
      this.avviso.tipiAnagrafica = this.tipiFormControl.value;
    }
    this.dialogRef.close(this.avviso);
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    this.dialogRef.close();
  }

}
