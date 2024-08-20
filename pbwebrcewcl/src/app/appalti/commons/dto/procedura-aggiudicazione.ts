/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { StepAggiudicazione } from './step-aggiudicazione';
export interface ProceduraAggiudicazione {
  cigProcAgg?: string;
  codProcAgg?: string;
  codice?: string;
  descProcAgg?: string;
  descTipologiaAggiudicazione?: string;
  idProceduraAggiudicaz?: number;
  idTipologiaAggiudicaz?: number;
  importo?: number;
  isModificabile?: boolean;
  iter?: Array<StepAggiudicazione>;
  iva?: number;
  linkAssocia?: string;
  linkModifica?: string;
  proceduraSelezionata?: boolean;
}
