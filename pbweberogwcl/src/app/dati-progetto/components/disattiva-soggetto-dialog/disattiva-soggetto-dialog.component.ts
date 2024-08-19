/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-disattiva-soggetto-dialog',
  templateUrl: './disattiva-soggetto-dialog.component.html',
  styleUrls: ['./disattiva-soggetto-dialog.component.scss']
})
export class DisattivaSoggettoDialogComponent implements OnInit {

  tipoDisattivazione: string = "";

  constructor(
    public dialogRef: MatDialogRef<DisattivaSoggettoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

  conferma() {
    this.dialogRef.close(this.tipoDisattivazione);
  }

  close() {
    this.dialogRef.close()
  }

}
