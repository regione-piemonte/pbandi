/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RigaRecuperoItem } from '../models/riga-recupero-item';
import { RigaModificaRecuperoItem } from "../models/riga-modifica-recupero-item";
export interface RequestSalvaRecuperi {
  dtRecupero?: string;
  estremiRecupero?: string;
  idAnnoContabile?: number;
  idProgetto?: number;
  idTipologiaRecupero?: number;
  noteRecupero?: string;
  recuperi?: Array<RigaRecuperoItem>;
}
