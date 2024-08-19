/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface RettificaForfettariaDTO {
  cigProcAgg?: string;
  codProcAgg?: string;
  codiceVisualizzato?: string;
  dataInserimento?: string;
  denominazioneBeneficiario?: string;
  descCategAnagrafica?: string;
  dtOraCreazione?: string;
  esitoDefinitivo?: string;
  esitoIntermedio?: string;
  flagRettificaDefinitivo?: string;
  flagRettificaIntermedio?: string;
  idAppalto?: number;
  idAppaltoChecklist?: number;
  idCategAnagrafica?: number;
  idDocumentoIndex?: number;
  idEsitoDefinitivo?: number;
  idEsitoIntermedio?: number;
  idProceduraAggiudicaz?: number;
  idProgetto?: number;
  idPropostaCertificaz?: number;
  idRettificaForfett?: number;
  idSoggettoBeneficiario?: number;
  nomeFile?: string;
  percRett?: number;
}
