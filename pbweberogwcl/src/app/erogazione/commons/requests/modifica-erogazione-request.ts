/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DatiCalcolati } from '../models/dati-calcolati';
import { Erogazione } from '../models/erogazione';
export interface ModificaErogazioneRequest {
  datiCalcolati?: DatiCalcolati;
  erogazione?: Erogazione;
  idProgetto?: number;
}
