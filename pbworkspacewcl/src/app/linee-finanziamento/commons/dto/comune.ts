/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Comune {
    constructor(
        public descComune: string,
        public descNazione: string,
        public descProvincia: string,
        public descRegione: string,
        public flagNazioneItaliana: string,
        public idComune: string,
        public idNazione: string,
        public idProvincia: string,
        public idRegione: string
    ) { }
}