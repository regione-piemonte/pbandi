/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { ErogazioneDTO } from '../dto/erogazione-dto';
import { GetDatiCalcolatiRequest } from './get-dati-calcolati-request';
export class GetDatiCalcolatiRequestClass implements GetDatiCalcolatiRequest {
  constructor(
    public erogazioneDTO?: ErogazioneDTO,
    public idProgetto?: number,
  ) { }
}
