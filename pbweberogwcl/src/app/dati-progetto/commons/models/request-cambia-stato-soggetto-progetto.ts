/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface RequestCambiaStatoSoggettoProgetto {
  progrSoggettiCorrelati?: number;
  progrSoggettoProgetto?: number;
  codiceFiscale?: string;
  idUtente?: number;
  idSoggetto?: number;
  codideRuolo?: string;
  ruoloNuovoUtente?: number;
  utenteAbilitatoProgetto?: string;
  idProgetto?: number;
}
