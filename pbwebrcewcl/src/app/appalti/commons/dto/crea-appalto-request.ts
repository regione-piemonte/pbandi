/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Appalto } from './appalto';
export interface CreaAppaltoRequest {
  appalto?: Appalto;
  idProceduraAggiudicaz?: number;
}
