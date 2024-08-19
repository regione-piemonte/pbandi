/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DatiCalcolati } from '../models/dati-calcolati';
import { Erogazione } from '../models/erogazione';
export interface SalvaErogazioneRequest {
  datiCalcolati?: DatiCalcolati;
  dettaglioErogazione?: Erogazione;
  idProgetto?: number;
}
