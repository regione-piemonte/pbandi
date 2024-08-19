/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface RigaRevocaItem {
  data?: string;
  hasCausaliErogazione?: boolean;
  hasIrregolarita?: boolean;
  idModalitaAgevolazione?: string;
  importoAgevolato?: number;
  importoErogato?: number;
  importoRecuperato?: number;
  importoRevocaNew?: number;
  importoRevocato?: number;
  isRigaModalita?: boolean;
  isRigaTotale?: boolean;
  label?: string;
  importoNuovaRevocaFormatted?: string;  //solo frontend
}
