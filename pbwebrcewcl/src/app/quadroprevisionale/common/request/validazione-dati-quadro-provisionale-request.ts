/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { QuadroPrevisionale } from '../dto/quadro-previsionale';
import { QuadroPrevisionaleItem } from '../dto/quadro-previsionale-item';
import { QuadroPrevisionaleComplessivoItem } from '../dto/quadro-previsionale-complessivo-item';
export interface ValidazioneDatiQuadroProvisionaleRequest {
  noteQuadroPrevisionale?: string;
  quadroPrevisionale?: QuadroPrevisionale;
  voci?: Array<QuadroPrevisionaleItem>;
  vociQuadroComplessivo?: Array<QuadroPrevisionaleComplessivoItem>;
}
