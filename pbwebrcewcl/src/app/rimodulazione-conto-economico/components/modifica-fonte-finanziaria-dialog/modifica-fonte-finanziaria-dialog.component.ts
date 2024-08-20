/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { FinanziamentoFonteFinanziaria } from '../../commons/dto/finanziamento-fonte-finanziaria';

@Component({
  selector: 'app-modifica-fonte-finanziaria-dialog',
  templateUrl: './modifica-fonte-finanziaria-dialog.component.html',
  styleUrls: ['./modifica-fonte-finanziaria-dialog.component.scss']
})
export class ModificaFonteFinanziariaDialogComponent implements OnInit {

  descFonteFinanziaria: string;
  importoQuota: number;
  importoQuotaFormatted: string;
  percentualeQuota: number;
  percentualeQuotaFormatted: string;
  estremiProvvedimento: string;
  importoAgevolato: number;

  @ViewChild('fonteForm', { static: true }) fonteForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<ModificaFonteFinanziariaDialogComponent>,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.descFonteFinanziaria = this.data.descFonteFinanziaria;
    this.importoQuota = this.data.importoQuota;
    this.importoQuotaFormatted = this.importoQuota ? this.sharedService.formatValue(this.importoQuota.toString()) : null;
    this.percentualeQuota = this.data.percentualeQuota;
    this.percentualeQuotaFormatted = this.percentualeQuota ? this.sharedService.formatValue(this.percentualeQuota.toString()) : null;
    this.estremiProvvedimento = this.data.estremiProvvedimento;
    this.importoAgevolato = this.data.importoAgevolato;
  }

  modelChangeImporto(inputValue: string) {
    if (!this.fonteForm.form.get('importo').hasError('pattern')) {
      this.importoQuota = this.sharedService.getNumberFromFormattedString(inputValue);
      this.importoQuotaFormatted = inputValue;
      this.percentualeQuota = this.importoQuota * 100 / this.importoAgevolato;
      this.percentualeQuotaFormatted = this.sharedService.formatValue(this.percentualeQuota.toString());
    }
  }

  changeImporto() {
    if (!this.fonteForm.form.get('importo').hasError('pattern')) {
      if (this.importoQuota !== null) {
        this.importoQuotaFormatted = this.sharedService.formatValue(this.importoQuota.toString());
      }
    }
  }

  modelChangePerc(inputValue: string) {
    if (!this.fonteForm.form.get('perc').hasError('pattern')) {
      this.percentualeQuota = this.sharedService.getNumberFromFormattedString(inputValue);
      this.percentualeQuotaFormatted = inputValue;
      this.importoQuota = this.importoAgevolato * this.percentualeQuota / 100;
      this.importoQuotaFormatted = this.sharedService.formatValue(this.importoQuota.toString());
    }
  }

  changePerc() {
    if (!this.fonteForm.form.get('perc').hasError('pattern')) {
      if (this.percentualeQuota !== null) {
        this.percentualeQuotaFormatted = this.sharedService.formatValue(this.percentualeQuota.toString());
      }
    }
  }

  salva() {
    let isError: boolean;
    if (this.importoQuota < 0 || this.importoQuota > 999999999999999.99) {
      this.fonteForm.form.get('importo').setErrors({ error: 'notValid' });
      this.fonteForm.form.get('importo').markAsTouched();
      isError = true;
    }
    if (this.percentualeQuota < 0 || this.percentualeQuota > 100) {
      this.fonteForm.form.get('perc').setErrors({ error: 'notValid' });
      this.fonteForm.form.get('perc').markAsTouched();
      isError = true;
    }
    if (this.estremiProvvedimento.length === 0) {
      this.fonteForm.form.get('estremi').setErrors({ error: 'notValid' });
      this.fonteForm.form.get('estremi').markAsTouched();
      isError = true;
    }
    if (isError) {
      return;
    }
    this.dialogRef.close({ importoQuota: this.importoQuota, percentualeQuota: this.percentualeQuota, estremiProvvedimento: this.estremiProvvedimento });
  }

  isSalvaDisabled() {
    if (this.importoQuota === null || this.percentualeQuota === null || !this.estremiProvvedimento || this.estremiProvvedimento.length === 0
      || (this.fonteForm.form.get('importo') && this.fonteForm.form.get('importo').errors)
      || (this.fonteForm.form.get('perc') && this.fonteForm.form.get('perc').errors)) {
      return true;
    }
    return false;
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
