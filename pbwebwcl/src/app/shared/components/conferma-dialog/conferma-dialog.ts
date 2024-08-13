/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-conferma-dialog',
  templateUrl: './conferma-dialog.html',
  styleUrls: ['./conferma-dialog.scss']
})
export class ConfermaDialog implements OnInit {

  message: string;

  constructor(
    public dialogRef: MatDialogRef<ConfermaDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.message = this.data.message;
  }

  si() {
    this.dialogRef.close("SI");
  }

  no() {
    this.dialogRef.close("NO");
  }

}
