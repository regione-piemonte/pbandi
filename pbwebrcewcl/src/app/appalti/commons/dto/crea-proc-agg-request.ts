/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { ProceduraAggiudicazione } from './procedura-aggiudicazione';
export interface CreaProcAggRequest {
  idProgetto?: number;
  proceduraAggiudicazione?: ProceduraAggiudicazione;
}
