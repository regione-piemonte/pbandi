/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface Erogazione {
  codCausaleErogazione?: string;
  codModalitaAgevolazione?: string;
  codModalitaErogazione?: string;
  codTipoDirezione?: string;
  dataContabile?: string;
  descCausaleErogazione?: string;
  descModalitaAgevolazione?: string;
  descModalitaErogazione?: string;
  descTipoDirezione?: string;
  idErogazione?: number;
  importoCalcolato?: number;
  importoErogazioneDaIterFinanziario?: number;
  importoErogazioneEffettiva?: number;
  importoResiduo?: number;
  note?: string;
  numero?: string;
  percErogazione?: number;
  percLimite?: number;
  percentualeErogazioneEffettiva?: number;
  percentualeErogazioneIterFinanziario?: number;
}
