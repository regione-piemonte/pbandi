/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DecodificaDTO } from "./decodifica-dto";

export class FiltroRicercaRendicontazionePartners {
    constructor(
        public visibile: boolean,
        public opzioni: Array<DecodificaDTO>,
        public partners: Array<DecodificaDTO>
    ) { }
}