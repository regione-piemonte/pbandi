/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-get-string-dialog',
  templateUrl: './get-string-dialog.component.html',
  styleUrls: ['./get-string-dialog.component.scss']
})

export class GetStringDialog implements OnInit {

  message: string;
  value: string;

  constructor(
    public dialogRef: MatDialogRef<GetStringDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.message = this.data.message;
    this.value = this.data.value;
  }

  onAnnulla() {
    this.dialogRef.close();
  }

  onConferma() {
    this.dialogRef.close(this.value);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
