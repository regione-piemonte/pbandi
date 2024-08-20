/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface QuadroPrevisionaleDTO {
  descVoce?: string;
  figli?: Array<QuadroPrevisionaleDTO>;
  haFigli?: boolean;
  idProgetto?: number;
  idQuadro?: number;
  idRigoQuadro?: number;
  idVoce?: number;
  idVocePadre?: number;
  importoDaRealizzare?: number;
  importoPreventivo?: number;
  importoRealizzato?: number;
  importoSpesaAmmessa?: number;
  isPeriodo?: boolean;
  macroVoce?: boolean;
  note?: string;
  periodo?: string;
}
