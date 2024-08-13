/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MessaggioDTO } from "./messaggio-dto";

export class EsitoOperazioneEconomia {
    constructor(
        public esito: boolean,
        public idEconomia: number,
        public msgs: MessaggioDTO
    ) { }
}