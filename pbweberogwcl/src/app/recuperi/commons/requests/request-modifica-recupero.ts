/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioRecupero } from '../models/dettaglio-recupero';
export interface RequestModificaRecupero {
  idProgetto?: number;
  recupero?: DettaglioRecupero;
}
