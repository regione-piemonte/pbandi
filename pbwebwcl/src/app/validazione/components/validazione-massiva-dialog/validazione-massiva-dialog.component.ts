/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-validazione-massiva-dialog',
  templateUrl: './validazione-massiva-dialog.component.html',
  styleUrls: ['./validazione-massiva-dialog.component.scss']
})
export class ValidazioneMassivaDialogComponent implements OnInit {

  isApplicaATuttoChecked: boolean = false;
  documentiSelezionati: Array<number>;

  constructor(
    public dialogRef: MatDialogRef<ValidazioneMassivaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.documentiSelezionati = this.data.documentiSelezionati;
  }

  respingi() {
    this.dialogRef.close({ operazione: "RESPINGERE", isApplicaATuttoChecked: this.isApplicaATuttoChecked });
  }

  invalida() {
    this.dialogRef.close({ operazione: "INVALIDARE", isApplicaATuttoChecked: this.isApplicaATuttoChecked });
  }

  valida() {
    this.dialogRef.close({ operazione: "VALIDARE", isApplicaATuttoChecked: this.isApplicaATuttoChecked });
  }

  close() {
    this.dialogRef.close();
  }

}
