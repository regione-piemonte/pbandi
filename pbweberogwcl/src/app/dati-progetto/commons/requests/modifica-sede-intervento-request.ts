/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { SedeProgettoDTO } from '../dto/sede-progetto-dto';
export interface ModificaSedeInterventoRequest {
  idSedeInterventoAttuale?: number;
  sedeIntervento?: SedeProgettoDTO;
}
