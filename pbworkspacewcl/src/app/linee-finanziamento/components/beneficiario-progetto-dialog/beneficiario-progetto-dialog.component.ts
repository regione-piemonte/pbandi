/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { BeneficiarioCspDTO } from '../../commons/dto/beneficiario-csp-dto';

@Component({
  selector: 'app-beneficiario-progetto-dialog',
  templateUrl: './beneficiario-progetto-dialog.component.html',
  styleUrls: ['./beneficiario-progetto-dialog.component.scss']
})
export class BeneficiarioProgettoDialogComponent implements OnInit {

  codiceFiscale: string;
  beneficiari: Array<BeneficiarioCspDTO>;
  beneficiarioSelected: BeneficiarioCspDTO;

  dataSource: MatTableDataSource<BeneficiarioCspDTO>;
  displayedColumns: string[] = ['radio', 'desc'];

  constructor(public dialogRef: MatDialogRef<BeneficiarioProgettoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.codiceFiscale = this.data.codiceFiscale;
    this.beneficiari = this.data.beneficiari;
    this.beneficiari.forEach(b => {
      b.sedeSelected = b.sediLegali[0];
    });
    this.beneficiarioSelected = this.beneficiari[0];
    console.log(this.beneficiari);
    this.dataSource = new MatTableDataSource<BeneficiarioCspDTO>(this.beneficiari);
  }
  seleziona() {
    this.dialogRef.close(this.beneficiarioSelected);
  }

  close() {
    this.dialogRef.close();
  }

}
