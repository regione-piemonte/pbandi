/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DatiAggiuntiviBandoLineaService } from '../../services/dati-aggiuntivi-bando-linea.service';

@Component({
  selector: 'app-nuova-area-scientifica-dialog',
  templateUrl: './nuova-area-scientifica-dialog.component.html',
  styleUrls: ['./nuova-area-scientifica-dialog.component.scss']
})
export class NuovaAreaScientificaDialogComponent implements OnInit {

  codice: string;
  areaScientifica: string;

  constructor(public dialogRef: MatDialogRef<NuovaAreaScientificaDialogComponent>) { }

  ngOnInit(): void {
  }

  /* ------------------ Inizio gestione pulsanti -------------------- */
  salva() {
    var comodoDatiOutput = new Array<any>();
    comodoDatiOutput.push(this.codice);
    comodoDatiOutput.push(this.areaScientifica);

    this.dialogRef.close({ data: comodoDatiOutput });
  }

  close() {
    this.dialogRef.close();
  }
  /* ------------------ Fine gestione pulsanti -------------------- */
}
