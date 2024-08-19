/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DsDettaglioRevocaIrregolarita } from './ds-dettaglio-revoca-irregolarita';
export interface DettaglioRevocaIrregolarita {
  dsDettagliRevocaIrregolarita?: Array<DsDettaglioRevocaIrregolarita>;
  idClassRevocaIrreg?: number;
  idDettRevocaIrreg?: number;
  idIrregolarita?: number;
  idRevoca?: number;
  importo?: number;
  importoAudit?: number;
  tipologia?: string;
}
