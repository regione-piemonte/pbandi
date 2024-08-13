/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { IndicatoreItem } from './indicatore-item';
export interface ResponseGetIndicatori {
  altriIndicatori?: Array<IndicatoreItem>;
  indicatoriMonitoraggio?: Array<IndicatoreItem>;
}
