/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-allega-verbale-dialog',
  templateUrl: './allega-verbale-dialog.component.html',
  styleUrls: ['./allega-verbale-dialog.component.scss']
})
export class AllegaVerbaleDialogComponent implements OnInit {

  codTipoChecklist: string;
  files: Array<File> = new Array<File>();

  constructor(
    public dialogRef: MatDialogRef<AllegaVerbaleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.codTipoChecklist = this.data.codTipoChecklist;
  }

  handleFileInput(files: Array<File>) {
    this.files.push(...files);
  }

  eliminaFile(index: number) {
    this.files.splice(index, 1);
  }

  conferma() {
    this.dialogRef.close({ esito: "S", files: this.files });
  }

  close() {
    this.dialogRef.close({ esito: "N", files: [] });
  }

}