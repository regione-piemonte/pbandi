/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MessaggioDTO } from "./messaggio-vo";

export class EsitoOperazioneDisassociaDTO { 
    constructor(
        public esito: boolean,
        public msg: MessaggioDTO
    ) { }
}