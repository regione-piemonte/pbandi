/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { MessaggioDTO } from '../dto/messaggio-dto';
export interface EsitoSalvaIrregolaritaDTO {
  esito?: boolean;
  idIrregolarita?: number;
  msgs?: Array<MessaggioDTO>;
}
