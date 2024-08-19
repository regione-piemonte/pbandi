/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DichiarazioneRinuncia } from '../models/dichiarazione-rinuncia';
export interface CreaComunicazioneRinunciaRequest {
  dichiarazioneRinuncia?: DichiarazioneRinuncia;
  idDelegato?: number;
  idProgetto?: number;
}
