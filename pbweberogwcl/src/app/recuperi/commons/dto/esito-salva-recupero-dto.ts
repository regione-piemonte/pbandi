/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import {MessaggioDTO} from "../../../shared/commons/dto/messaggio-dto";

export interface EsitoSalvaRecuperoDTO {
  esito?: boolean;
  msgs?: Array<MessaggioDTO>;
}
