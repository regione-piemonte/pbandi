/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DichiarazioneRinuncia } from '../models/dichiarazione-rinuncia';
import { CreaComunicazioneRinunciaRequest } from './crea-comunicazione-rinuncia-request';
export class CreaComunicazioneRinunciaRequestClass implements CreaComunicazioneRinunciaRequest {
  constructor(
    public dichiarazioneRinuncia?: DichiarazioneRinuncia,
    public idDelegato?: number,
    public idProgetto?: number,
  ) { }
}
