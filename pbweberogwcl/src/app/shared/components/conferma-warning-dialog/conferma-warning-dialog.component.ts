/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-conferma-warning-dialog',
  templateUrl: './conferma-warning-dialog.component.html',
  styleUrls: ['./conferma-warning-dialog.component.scss']
})
export class ConfermaWarningDialogComponent implements OnInit {

  title: string;
  message: string;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<ConfermaWarningDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.title = this.data.title;
    this.message = this.data.message;
    if (this.data.messageWarning) {
      this.showMessageWarning(this.data.messageWarning);
    }
  }

  continua() {
    this.dialogRef.close("OK");
  }

  close() {
    this.dialogRef.close();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

}
