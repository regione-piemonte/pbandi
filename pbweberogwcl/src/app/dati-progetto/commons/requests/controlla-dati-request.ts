/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface ControllaDatiRequest {
  codCausaleErogSel?: string;
  importoCalcolato?: number;
  importoErogazioneEffettiva?: number;
  importoResiduoSpettante?: number;
  percErogazione?: number;
  percLimite?: number;
}
