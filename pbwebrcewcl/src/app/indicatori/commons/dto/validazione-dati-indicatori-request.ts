/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { IndicatoreItem } from './indicatore-item';
export interface ValidazioneDatiIndicatoriRequest {
  idProgetto?: number;
  indicatori?: Array<IndicatoreItem>;
  altriIndicatori?: Array<IndicatoreItem>;
}