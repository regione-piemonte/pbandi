/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { QuadroPrevisionaleComplessivoItem } from './quadro-previsionale-complessivo-item';
import { QuadroPrevisionale } from './quadro-previsionale';
import { QuadroPrevisionaleItem } from './quadro-previsionale-item';
export interface ResponseGetQuadroPrevisionale {
  idProgetto?: number;
  note?: string;
  quadroComplessivoList?: Array<QuadroPrevisionaleComplessivoItem>;
  quadroPrevisionale?: QuadroPrevisionale;
  vociWeb?: Array<QuadroPrevisionaleItem>;
}
