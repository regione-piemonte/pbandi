/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import {Irregolarita} from "../models/irregolarita";

export interface RequestSalvaIrregolarita {
  idProgetto?: number;
  irregolarita?: Irregolarita;
}
