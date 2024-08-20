/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificaDTO } from '../../commons/dto/notifica-dto';

@Component({
  selector: 'app-notifiche-affidamenti-dialog',
  templateUrl: './notifiche-affidamenti-dialog.component.html',
  styleUrls: ['./notifiche-affidamenti-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NotificheAffidamentiDialogComponent implements OnInit {

  notifiche: Array<NotificaDTO>;

  constructor(
    public dialogRef: MatDialogRef<NotificheAffidamentiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.notifiche = this.data.notifiche;
  }

  close() {
    this.dialogRef.close();
  }
}
