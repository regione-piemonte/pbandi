/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface ChecklistRettificaForfettariaDTO {
  cigProcAgg?: string;
  codProcAgg?: string;
  descTipologiaAppalto?: string;
  esitoDefinitivo?: string;
  esitoIntermedio?: string;
  flagRettificaDefinitivo?: string;
  flagRettificaIntermedio?: string;
  idAppalto?: number;
  idAppaltoChecklist?: number;
  idChecklist?: number;
  idDocumentoIndex?: number;
  idEsitoDefinitivo?: number;
  idEsitoIntermedio?: number;
  idProceduraAggiudicaz?: number;
  idStatoTipoDocIndex?: number;
  idTipologiaAppalto?: number;
  importo?: number;
  importoContratto?: number;
  nomeFile?: string;
  oggettoAppalto?: string;
}
