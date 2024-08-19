/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RigaModificaRevocaItem } from './riga-modifica-revoca-item';
export interface RequestFindIrregolarita {
  idRevoca?: number;
  revoche?: Array<RigaModificaRevocaItem>;
}
