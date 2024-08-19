/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RevocaIrregolarita } from './revoca-irregolarita';
export interface Revoca {
  idMotivoRevoca?: number;
  idRevoca?: number;
  importoRevocato?: number;
  irregolarita?: Array<RevocaIrregolarita>;
}
