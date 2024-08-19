/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Erogazione } from './erogazione';
export interface EsitoControllaDati {
  erogazione?: Erogazione;
  esito?: boolean;
  message?: string;
}
