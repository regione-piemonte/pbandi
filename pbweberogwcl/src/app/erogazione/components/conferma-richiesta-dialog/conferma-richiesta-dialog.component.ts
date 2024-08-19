/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-conferma-richiesta-dialog',
  templateUrl: './conferma-richiesta-dialog.component.html',
  styleUrls: ['./conferma-richiesta-dialog.component.scss']
})
export class ConfermaRichiestaDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ConfermaRichiestaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

  invia() {
    this.dialogRef.close("OK");
  }

  close() {
    this.dialogRef.close();
  }
}
