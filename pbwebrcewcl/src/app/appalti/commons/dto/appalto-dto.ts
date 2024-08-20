/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface AppaltoDTO {
  bilancioPreventivo?: number;
  descProcAgg?: string;
  descrizioneProcAgg?: string;
  dtConsegnaLavori?: string;
  dtFirmaContratto?: string;
  dtGuri?: string;
  dtGuue?: string;
  dtInizioPrevista?: string;
  dtInserimento?: string;
  dtQuotNazionali?: string;
  dtWebOsservatorio?: string;
  dtWebStazAppaltante?: string;
  idAppalto?: number;
  idProceduraAggiudicaz?: number;
  idProgetto?: number;
  idTipoAffidamento?: number;
  idTipoPercettore?: number;
  idTipologiaAppalto?: number;
  idUtenteAgg?: number;
  idUtenteIns?: number;
  importoContratto?: number;
  importoRibassoAsta?: number;
  impresaAppaltatrice?: string;
  interventoPisu?: string;
  oggettoAppalto?: string;
  percentualeRibassoAsta?: number;
  sopraSoglia?: string;
}
