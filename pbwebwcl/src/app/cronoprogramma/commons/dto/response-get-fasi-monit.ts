/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DatiCronoprogramma } from './dati-cronoprogramma';
import { CronoprogrammaItem } from './cronoprogramma-item';
export interface ResponseGetFasiMonit {
  datiCrono?: DatiCronoprogramma;
  item?: Array<CronoprogrammaItem>;
}
