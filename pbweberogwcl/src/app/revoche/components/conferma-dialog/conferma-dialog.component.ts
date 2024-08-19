/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-conferma-dialog',
  templateUrl: './conferma-dialog.component.html',
  styleUrls: ['./conferma-dialog.component.scss']
})
export class ConfermaDialogComponent implements OnInit {

  msg: string;

  constructor(
    public dialogRef: MatDialogRef<ConfermaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string
  ) { }

  ngOnInit(): void {
    this.msg = this.data;
  }

  conferma() {
    this.dialogRef.close({ data: true });
  }

  annulla() {
    this.dialogRef.close({ data: false });
  }
}
