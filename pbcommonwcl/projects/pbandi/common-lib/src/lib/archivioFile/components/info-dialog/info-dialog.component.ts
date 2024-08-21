/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InfoAssociazioneFileVo } from './../../commons/vo/info-associazione-file-vo';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss']
})
export class InfoDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<InfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: InfoAssociazioneFileVo
  ) { }

  infoFile: InfoAssociazioneFileVo;
  nomeFile: string;

  ngOnInit(): void {
    this.infoFile = this.data;

    /* NOME FILE*/ /*--> forse può essere vuoto*/
    this.nomeFile = this.infoFile.nomeFile
  }

  convertiData(data: string) {
    var comodo = data.split("T")[0].split("-");

    return comodo[2] + "/" + comodo[1] + "/" + comodo[0];
  }

  close() {
    this.dialogRef.close();
  }
}
