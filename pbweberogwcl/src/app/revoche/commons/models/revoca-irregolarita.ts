/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioRevocaIrregolarita } from './dettaglio-revoca-irregolarita';
export interface RevocaIrregolarita {
  descPeriodoVisualizzata?: string;
  dettagliRevocaIrregolarita?: Array<DettaglioRevocaIrregolarita>;
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
