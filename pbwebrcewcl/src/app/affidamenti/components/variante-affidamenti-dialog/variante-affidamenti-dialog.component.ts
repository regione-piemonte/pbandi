/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CodiceDescrizioneDTO } from 'src/app/shared/commons/dto/codice-descrizione-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { SalvaVarianteRequest } from '../../commons/requests/salva-variante-request';

@Component({
  selector: 'app-variante-affidamenti-dialog',
  templateUrl: './variante-affidamenti-dialog.component.html',
  styleUrls: ['./variante-affidamenti-dialog.component.scss']
})
export class VarianteAffidamentiDialogComponent implements OnInit {

  tipologieVarianti: Array<CodiceDescrizioneDTO>;
  tipologiaSelected: CodiceDescrizioneDTO;
  importo: number;
  importoFormatted: string;
  note: string;
  disabled: boolean;

  constructor(
    public dialogRef: MatDialogRef<VarianteAffidamentiDialogComponent>,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.tipologieVarianti = this.data.tipologieVarianti;
    this.tipologiaSelected = this.tipologieVarianti.find(t => +t.codice === this.data.idTipologiaVariante);
    this.importo = this.data.importo;
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
    this.note = this.data.note;
    this.disabled = this.data.disabled;
  }

  setImporto() {
    this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
  }

  annulla() {
    this.dialogRef.close("ANNULLA");
  }

  salva() {
    this.dialogRef.close(new SalvaVarianteRequest(+this.tipologiaSelected.codice, this.importo, this.note, null, null));
  }

}
