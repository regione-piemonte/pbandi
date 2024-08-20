/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SubcontrattoRequest {
    constructor(
        public idSubcontrattoAffidamento: number,
        public idFornitore: number,
        public idAppalto: number,
        public idSubcontraente: number,
        public riferimentoSubcontratto: string,
        public dtSubcontratto: Date,
        public importoSubcontratto: number
    ) { }
}