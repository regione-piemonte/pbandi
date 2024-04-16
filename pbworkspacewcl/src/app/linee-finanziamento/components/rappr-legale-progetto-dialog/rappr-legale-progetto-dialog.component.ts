/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { RapprLegaleCspDTO } from '../../commons/dto/rappr-legale-csp-dto';

@Component({
  selector: 'app-rappr-legale-progetto-dialog',
  templateUrl: './rappr-legale-progetto-dialog.component.html',
  styleUrls: ['./rappr-legale-progetto-dialog.component.scss']
})
export class RapprLegaleProgettoDialogComponent implements OnInit {

  codiceFiscale: string;
  rappresentanti: Array<RapprLegaleCspDTO>;
  rappresentanteSelected: RapprLegaleCspDTO;

  dataSource: MatTableDataSource<RapprLegaleCspDTO>;
  displayedColumns: string[] = ['radio', 'desc'];

  constructor(public dialogRef: MatDialogRef<RapprLegaleProgettoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.codiceFiscale = this.data.codiceFiscale;
    this.rappresentanti = this.data.rappresentanti;
    this.rappresentanti.forEach(r => {
      r.indirizzoSelected = r.indirizzi[0];
    });
    this.rappresentanteSelected = this.rappresentanti[0];
    console.log(this.rappresentanti);
    this.dataSource = new MatTableDataSource<RapprLegaleCspDTO>(this.rappresentanti);
  }
  seleziona() {
    this.dialogRef.close(this.rappresentanteSelected);
  }

  close() {
    this.dialogRef.close();
  }

}
