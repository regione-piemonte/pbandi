/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-storico-richiesta',
  templateUrl: './storico-richiesta.component.html',
  styleUrls: ['./storico-richiesta.component.scss']
})
export class StoricoRichiestaComponent{
  constructor(@Inject(MAT_DIALOG_DATA) public data: StoricoRichiesta){}
}

export class StoricoRichiesta {
  colonne: string[];
  storicoRichiesta: any;
}

