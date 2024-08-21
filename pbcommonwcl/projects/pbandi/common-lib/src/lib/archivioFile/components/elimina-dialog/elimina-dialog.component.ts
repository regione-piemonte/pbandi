/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-elimina-dialog',
  templateUrl: './elimina-dialog.component.html',
  styleUrls: ['./elimina-dialog.component.scss']
})
export class EliminaDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<EliminaDialogComponent>
  ) { }

  ngOnInit(): void {

  }

  elimina() {
    this.dialogRef.close({ data: true });
  }

  annulla() {
    this.dialogRef.close({ data: false });
  }
}
