/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-gestione-richieste',
  templateUrl: './dialog-gestione-richieste.component.html',
  styleUrls: ['./dialog-gestione-richieste.component.scss']
})
export class DialogGestioneRichiesteComponent implements OnInit {

  title: string;
  message: string;
  constructor(
    public dialogRef: MatDialogRef<DialogGestioneRichiesteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

  onConfirm(): void {
    // Close the dialog, return true
    this.dialogRef.close(true);
  }

  close(): void {
    this.dialogRef.close();
  }


}
