/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { AppaltoDTO } from './appalto-dto';
export interface EsitoGestioneAppalti {
  appalto?: AppaltoDTO;
  esito?: boolean;
  message?: string;
  params?: Array<string>;
}
