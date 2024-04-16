/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-contatti-dialog',
  templateUrl: './contatti-dialog.html',
  styleUrls: ['./contatti-dialog.scss']
})
export class ContattiDialog implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ContattiDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

}
