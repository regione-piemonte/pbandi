/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface DettaglioSoggettoProgettoDTO {
  capRes?: string;
  codiceFiscale?: string;
  cognome?: string;
  dataFineValidita?: string;
  dataNascita?: string;
  idComune?: string;
  idComuneRes?: string;
  idIndirizzo?: string;
  idNazione?: string;
  idNazioneRes?: string;
  idPersonaFisica?: string;
  idProgetto?: string;
  idProvincia?: string;
  idProvinciaRes?: string;
  idRegioneRes?: string;
  idSoggetto?: string;
  idTipoSoggettoCorrelato?: string;
  idTipoSoggettoCorrelatoAttuale?: string;
  indirizzoRes?: string;
  nome?: string;
  numCivicoRes?: string;
  progrSoggettiCorrelati?: string;
  progrSoggettoProgetto?: string;
  sesso?: string;
  accessoSistema?: boolean; //su java si chiama isAccessoSistema, ma se lo chiamo così non funziona
  codiceRuolo?: string;
}
