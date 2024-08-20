/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { StepAggiudicazione } from './step-aggiudicazione';
export interface ProceduraAggiudicazioneDTO {
  cigProcAgg?: string;
  codProcAgg?: string;
  codice?: string;
  descProcAgg?: string;
  descTipologiaAggiudicazione?: string;
  dtAggiudicazione?: string;
  idMotivoAssenzaCig?: number;
  idProceduraAggiudicaz?: number;
  idProgetto?: number;
  idTipologiaAggiudicaz?: number;
  importo?: number;
  importoPercentuale?: number;
  iter?: Array<StepAggiudicazione>;
  iva?: number;
  oggettoAffidamento?: string;
  percentuale?: number;
}
