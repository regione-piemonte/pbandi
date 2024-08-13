/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-modifica-parametro-monitoraggio-dialog.component',
  templateUrl: './modifica-parametro-monitoraggio-dialog.component.html',
  styleUrls: ['./modifica-parametro-monitoraggio-dialog.component.scss']
})
export class ModificaParametroMonitoraggioDialog implements OnInit { 

  descrizione : string
  numGiorni : number

  constructor(public dialogRef: MatDialogRef<ModificaParametroMonitoraggioDialog>,
      @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {

    this.descrizione = this.data.descrizione
    this.numGiorni = this.data.numGironi

  }

  /* ------------------ Inizio gestione pulsanti -------------------- */
  salva() {

    let output : any = {numGironi : this.numGiorni}
    this.dialogRef.close({ data: output });
  }

  close() {
    this.dialogRef.close();
  }
  /* ------------------ Fine gestione pulsanti -------------------- */
}
