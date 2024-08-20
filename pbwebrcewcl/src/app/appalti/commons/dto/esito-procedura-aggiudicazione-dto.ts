/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { MessaggioDTO } from './messaggio-dto';
import { ProceduraAggiudicazioneDTO } from './procedura-aggiudicazione-dto';
export interface EsitoProceduraAggiudicazioneDTO {
  esito?: boolean;
  msgs?: Array<MessaggioDTO>;
  proceduraAggiudicazione?: ProceduraAggiudicazioneDTO;
}
