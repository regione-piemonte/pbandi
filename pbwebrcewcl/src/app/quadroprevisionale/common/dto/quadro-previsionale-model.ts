/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import {QuadroPrevisionaleComplessivoItem} from "./quadro-previsionale-complessivo-item";
import {QuadroPrevisionaleItem} from "./quadro-previsionale-item";

export interface QuadroPrevisionaleModel {
  voci: Array<QuadroPrevisionaleItem>;
  vociQuadroComplessivo: Array<QuadroPrevisionaleComplessivoItem>;


}
