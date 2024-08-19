/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { FiltroRilevazione } from './filtro-rilevazione';
export interface RequestGetProgettiCampione {
  filtro?: FiltroRilevazione;
  idProgetti?: string;
}
