/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Irregolarita } from './irregolarita';
export interface RequestModificaIrregolarita {
  idProgetto?: number;
  irregolarita?: Irregolarita;
  idU: number;
	idBeneficiario: number;
	codiceFiscale: string;
}
