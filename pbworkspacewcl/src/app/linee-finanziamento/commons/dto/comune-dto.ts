/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ComuneDTO {
    constructor(
        public idNazione: string,
        public idRegione: string,
        public idProvincia: string,
        public idComune: string,
        public flagNazioneItaliana: string,
        public descNazione: string,
        public descRegione: string,
        public descProvincia: string,
        public descComune: string
    ) { }
}