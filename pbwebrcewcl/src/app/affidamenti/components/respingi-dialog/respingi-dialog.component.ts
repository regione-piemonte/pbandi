/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-respingi-dialog',
  templateUrl: './respingi-dialog.component.html',
  styleUrls: ['./respingi-dialog.component.scss']
})
export class RespingiDialogComponent implements OnInit {

  motivazione: string;

  constructor(
    public dialogRef: MatDialogRef<RespingiDialogComponent>
  ) { }

  ngOnInit(): void {
  }

  respingi() {
    this.dialogRef.close(this.motivazione);
  }

  close() {
    this.dialogRef.close();
  }

}
