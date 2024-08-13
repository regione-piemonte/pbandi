/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-nuovo-estremo-bancario-dialog',
  templateUrl: './nuovo-estremo-bancario-dialog.component.html',
  styleUrls: ['./nuovo-estremo-bancario-dialog.component.scss']
})
export class NuovoEstremoBancarioDialog implements OnInit { 

  iban: string
  tipologiaConto: string
  moltiplicatore: number

  constructor(public dialogRef: MatDialogRef<NuovoEstremoBancarioDialog>) { }

  ngOnInit(): void {
  }

  /* ------------------ Inizio gestione pulsanti -------------------- */
  salva() {
    var comodoDatiOutput = new Array<any>();
    comodoDatiOutput.push(this.iban);
    comodoDatiOutput.push(this.tipologiaConto);
    comodoDatiOutput.push(this.moltiplicatore);

    this.dialogRef.close({ data: comodoDatiOutput });
  }

  close() {
    this.dialogRef.close();
  }
  /* ------------------ Fine gestione pulsanti -------------------- */
}
