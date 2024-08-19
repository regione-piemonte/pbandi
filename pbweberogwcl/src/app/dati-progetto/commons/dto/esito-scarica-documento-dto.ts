/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface EsitoScaricaDocumentoDTO {
  bytesDocumento?: Array<ArrayBuffer>;
  esito?: boolean;
  message?: string;
  nomeFile?: string;
  params?: Array<string>;
}
