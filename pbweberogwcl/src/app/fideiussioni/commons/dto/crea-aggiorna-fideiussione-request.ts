/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Fideiussione } from './fideiussione';
export interface CreaAggiornaFideiussioneRequest {
  fideiussione?: Fideiussione;
  idProgetto?: number;
}
