/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioRevoca } from './dettaglio-revoca';
export interface RequestModificaDisimpegno {
  dettaglioRevoca?: DettaglioRevoca;
  idProgetto?: number;
}
