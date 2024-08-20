/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface Appalto {
  bilancioPreventivo?: number;
  descrizioneProceduraAggiudicazione?: string;
  dtConsegnaLavori?: string;
  dtFirmaContratto?: string;
  dtPrevistaInizioLavori?: string;
  dtPubGURI?: string;
  dtPubGUUE?: string;
  dtPubLLPP?: string;
  dtPubQuotidianiNazionali?: string;
  dtPubStazioneAppaltante?: string;
  idAppalto?: number;
  idProceduraAggiudicazione?: number;
  idTipologiaAppalto?: number;
  identificativoIntervento?: string;
  importoContratto?: number;
  importoRibassoAsta?: number;
  impresaAppaltatrice?: string;
  oggettoAppalto?: string;
  percentualeRibassoAsta?: number;
  proceduraAggiudicazione?: string;
}
