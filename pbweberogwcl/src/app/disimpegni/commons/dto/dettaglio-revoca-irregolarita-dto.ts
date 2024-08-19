/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DsDettaglioRevocaIrregolaritaDTO } from '../dto/ds-dettaglio-revoca-irregolarita-dto';
export interface DettaglioRevocaIrregolaritaDTO {
  dsDettagliRevocaIrregolarita?: Array<DsDettaglioRevocaIrregolaritaDTO>;
  idClassRevocaIrreg?: number;
  idDettRevocaIrreg?: number;
  idIrregolarita?: number;
  idRevoca?: number;
  importo?: number;
  importoAudit?: number;
  tipologia?: string;
}
