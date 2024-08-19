/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Fideiussione } from '../models/fideiussione';
import { CreaAggiornaFideiussioneRequest } from './crea-aggiorna-fideiussione-request';
export class CreaAggiornaFideiussioneRequestClass implements CreaAggiornaFideiussioneRequest {
  constructor(
    public fideiussione?: Fideiussione,
    public idProgetto?: number,
  ) { }
}
