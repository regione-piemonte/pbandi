/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RichiestaErogazioneDTO } from '../dto/richiesta-erogazione-dto';
export interface EsitoRichiestaErogazioneDTO {
  esito?: boolean;
  idDocIndex?: number;
  isRegolaAttiva?: boolean;
  messages?: Array<string>;
  richiestaErogazione?: RichiestaErogazioneDTO;
}
