/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ProgettoCampione } from '../../commons/models/progetto-campione';

@Component({
  selector: 'app-mostra-progetti-campione-dialog',
  templateUrl: './mostra-progetti-campione-dialog.html',
  styleUrls: ['./mostra-progetti-campione-dialog.scss']
})
export class MostraProgettiCampioneDialogComponent implements OnInit {

  displayedColumnsPresenti: string[] = ['dataCampione', 'idProgetto', 'codiceProgetto', 'titoloProgetto', 'beneficiario', 'bandoLinea'];
  dataSourcePresenti: MatTableDataSource<ProgettoCampione>;

  constructor(
    public dialogRef: MatDialogRef<MostraProgettiCampioneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.dataSourcePresenti = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.dataSourcePresenti = this.data;
  }

  annulla() {
    this.dialogRef.close({ data: false });
  }
}
