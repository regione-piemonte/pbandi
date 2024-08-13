/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RettificaVoceItem } from '../../commons/dto/rettifica-voce-item';

@Component({
  selector: 'app-dettaglio-rettifiche-dialog',
  templateUrl: './dettaglio-rettifiche-dialog.component.html',
  styleUrls: ['./dettaglio-rettifiche-dialog.component.scss']
})
export class DettaglioRettificheDialogComponent implements OnInit {

  rettifiche: Array<RettificaVoceItem>;

  constructor(
    public dialogRef: MatDialogRef<DettaglioRettificheDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.rettifiche = this.data.rettifiche;
  }

  close() {
    this.dialogRef.close();
  }

}
