/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-richiesta-integrazione-dialog',
  templateUrl: './richiesta-integrazione-dialog.component.html',
  styleUrls: ['./richiesta-integrazione-dialog.component.scss']
})
export class RichiestaIntegrazioneDialogComponent implements OnInit {

  motivazione: string;

  modules = {
    toolbar: [
      ['bold', 'italic', 'underline'],        // toggled buttons
      [{ 'list': 'ordered' }, { 'list': 'bullet' }],
      [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
      [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
    ]
  };

  constructor(
    public dialogRef: MatDialogRef<RichiestaIntegrazioneDialogComponent>
  ) { }

  ngOnInit(): void {
  }

  inviaRichiesta() {
    this.dialogRef.close(this.motivazione);
  }

  close() {
    this.dialogRef.close();
  }

}
