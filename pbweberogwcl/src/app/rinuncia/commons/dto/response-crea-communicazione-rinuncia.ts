/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */

import { DichiarazioneDiRinunciaDTO } from "./dichiarazione-di-rinuncia-dto";

export interface ResponseCreaCommunicazioneRinuncia {
  dichiarazioneRinuncia?: DichiarazioneDiRinunciaDTO;
  invioDigitale?: boolean;
  message?: string;
}
