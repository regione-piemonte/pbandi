/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { ErogazioneDTO } from '../dto/erogazione-dto';
export interface GetDatiCalcolatiRequest {
  erogazioneDTO?: ErogazioneDTO;
  idProgetto?: number;
}
