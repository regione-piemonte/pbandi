/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { FiltroRicercaFideiussione } from '../models/filtro-ricerca-fideiussione';
export interface CercaFideiussioniRequest {
  filtro?: FiltroRicercaFideiussione;
  idProgetto?: number;
}
