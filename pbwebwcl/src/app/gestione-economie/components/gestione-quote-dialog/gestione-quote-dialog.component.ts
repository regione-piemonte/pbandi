/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { SharedService } from 'src/app/shared/services/shared.service';
import { QuotaDTO } from '../../commons/dto/quota-dto';

@Component({
  selector: 'app-gestione-quote-dialog',
  templateUrl: './gestione-quote-dialog.component.html',
  styleUrls: ['./gestione-quote-dialog.component.scss']
})
export class GestioneQuoteDialogComponent implements OnInit {

  type: string;
  quote: Array<QuotaDTO>;
  importoCeduto: number;
  isFromRimodulazione: boolean;

  displayedColumns: string[] = ['soggetto', 'perc', 'importo'];
  dataSource: MatTableDataSource<QuotaDTO> = new MatTableDataSource<QuotaDTO>([]);

  @ViewChild('tableForm', { static: true }) tableForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<GestioneQuoteDialogComponent>,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.type = this.data.type;
    this.importoCeduto = this.data.importoCeduto;
    this.isFromRimodulazione = this.data.isFromRimodulazione;
    if (this.data.quote) {
      this.quote = new Array<QuotaDTO>();
      this.data.quote.forEach(q => this.quote.push(Object.assign({}, q)));
      this.quote.forEach(q => {
        if (q.impQuotaEconSoggFinanziat !== null) {
          q.impQuotaEconSoggFinanziatFormatted = this.sharedService.formatValue(q.impQuotaEconSoggFinanziat.toString());
        }
      });
      this.dataSource = new MatTableDataSource<QuotaDTO>(this.quote);
    }
  }

  setImporto(quota: QuotaDTO) {
    quota.impQuotaEconSoggFinanziat = this.sharedService.getNumberFromFormattedString(quota.impQuotaEconSoggFinanziatFormatted);
    if (quota.impQuotaEconSoggFinanziat !== null) {
      quota.impQuotaEconSoggFinanziatFormatted = this.sharedService.formatValue(quota.impQuotaEconSoggFinanziat.toString());
    }
  }

  salva() {
    let tot: number = 0;
    for (let quota of this.quote) {
      tot += quota.impQuotaEconSoggFinanziat ? quota.impQuotaEconSoggFinanziat : 0;
    }
    if (+tot.toFixed(2) === this.importoCeduto) {
      this.dialogRef.close(this.quote);
    } else {
      for (let quota of this.quote) {
        this.tableForm.form.get('importo' + quota.idSoggettoFinanziatore).setErrors({ error: 'error' });
      }
      this.tableForm.form.markAllAsTouched();
      this.showMessageError("La somma degli importi deve corrispondere all'importo ceduto.");
    }
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
