/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SubcontrattoAffidDTO {
    constructor(
        public idSubcontrattoAffidamento: number,
        public idFornitore: number,
        public idAppalto: number,
        public idSubcontraente: number,
        public dtSubcontratto: Date,
        public riferimentoSubcontratto: string,
        public importoSubcontratto: number,
        public denominazioneSubcontraente: string,
        public cfPivaSubcontraente: string
    ) { }
}