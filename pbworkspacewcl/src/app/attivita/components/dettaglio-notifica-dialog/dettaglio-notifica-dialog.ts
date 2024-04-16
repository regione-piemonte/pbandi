/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PbandiTNotificaProcessoVO } from '../../commons/vo/pbandi-t-notifica-processo-vo';

@Component({
  selector: 'app-dettaglio-notifica-dialog',
  templateUrl: './dettaglio-notifica-dialog.html',
  styleUrls: ['./dettaglio-notifica-dialog.scss']
})
export class DettaglioNotificaDialog implements OnInit {

  notifica: PbandiTNotificaProcessoVO;

  constructor(
    public dialogRef: MatDialogRef<DettaglioNotificaDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.notifica = this.data.notifica;
  }

  close(){
    this.dialogRef.close();
  }

}
