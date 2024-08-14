/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ProgettoCertificazioneIntermediaFinaleDTO } from "../dto/progetto-certificazione-intermedia-finale-dto";

export class AggiornaDatiIntermediaFinaleRequest {
    constructor(
        public elencoProgettiIntermediaFinale: Array<ProgettoCertificazioneIntermediaFinaleDTO>
    ) { }

}