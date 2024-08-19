/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioRevocaIrregolaritaDTO } from '../dto/dettaglio-revoca-irregolarita-dto';
export interface RevocaIrregolaritaDTO {
  descPeriodoVisualizzata?: string;
  dettagliRevocaIrregolarita?: Array<DettaglioRevocaIrregolaritaDTO>;
  dtFineValidita?: string;
  idIrregolarita?: number;
  idRevoca?: number;
  importoAgevolazioneIrreg?: number;
  importoIrregolarita?: number;
  motivoRevocaIrregolarita?: string;
  notePraticaUsata?: string;
  quotaIrreg?: number;
  tipoIrregolarita?: string;
}
