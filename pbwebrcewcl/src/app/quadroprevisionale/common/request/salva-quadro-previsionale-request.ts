/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { QuadroPrevisionaleDTO } from '../dto/quadro-previsionale-dto';
export interface SalvaQuadroPrevisionaleRequest {
  idProgetto?: number;
  quadroPrevisionale?: QuadroPrevisionaleDTO;
}
