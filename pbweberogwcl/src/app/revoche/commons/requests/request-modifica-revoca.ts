/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioRevoca } from '../models/dettaglio-revoca';
export interface RequestModificaRevoca {
  idProgetto?: number;
  revoca?: DettaglioRevoca;
}
