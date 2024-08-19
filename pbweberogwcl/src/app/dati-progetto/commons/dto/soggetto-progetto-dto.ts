/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface SoggettoProgettoDTO {
  codUniIpa?: string;
  codiceFiscaleSoggetto?: string;
  denominazione?: string;
  descBreveTipoAnagrafica?: string;
  descTipoAnagrafica?: string;
  descTipoSoggetto?: string;
  descTipoSoggettoCorrelato?: string;
  dtFineValidita?: string;
  flagPubblicoPrivato?: number;
  idTipoAnagrafica?: number;
  idTipoSoggetto?: number;
  idTipoSoggettoCorrelato?: number;
  progrSoggettiCorrelati?: number;
  progrSoggettoProgetto?: number;
  inAttesaEsito?: boolean;
  abilitatoAccesso?: string;
  rifiutata?: boolean;
  idSoggetto?: number;
  disattivazioneDefinitiva?: boolean;
  isModificabile?: boolean;  //solo frontend
  isDettaglio?: boolean;  //solo frontend
  isWarning?: boolean;  //solo frontend
  hasSlide?: boolean;  //solo frontend
  isRifiutabile?: boolean;  //solo frontend
}
