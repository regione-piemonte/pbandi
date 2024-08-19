/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DatiCalcolati } from '../models/dati-calcolati';
import { Erogazione } from '../models/erogazione';
import { SalvaErogazioneRequest } from './salva-erogazione-request';
export class SalvaErogazioneRequestClass implements SalvaErogazioneRequest {
  constructor(
    public datiCalcolati?: DatiCalcolati,
    public dettaglioErogazione?: Erogazione,
    public idProgetto?: number,
  ) { }
}
