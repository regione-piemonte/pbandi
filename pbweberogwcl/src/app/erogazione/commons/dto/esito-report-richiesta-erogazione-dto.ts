/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface EsitoReportRichiestaErogazioneDTO {
  esito?: boolean;
  idDocIndex?: number;
  messages?: Array<string>;
  nomeReport?: string;
  pdfBytes?: Array<ArrayBuffer>;
}
