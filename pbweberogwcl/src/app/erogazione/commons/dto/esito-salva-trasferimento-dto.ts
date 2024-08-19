/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { MessaggioDTO } from '../dto/messaggio-dto';
export interface EsitoSalvaTrasferimentoDTO {
  esito?: boolean;
  idTrasferimento?: number;
  msgs?: Array<MessaggioDTO>;
}
