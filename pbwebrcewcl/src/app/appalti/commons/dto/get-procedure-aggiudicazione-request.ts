/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { FiltroProcedureAggiudicazione } from './filtro-procedure-aggiudicazione';
export interface GetProcedureAggiudicazioneRequest {
  filtro?: FiltroProcedureAggiudicazione;
  idProgetto?: number;
}
