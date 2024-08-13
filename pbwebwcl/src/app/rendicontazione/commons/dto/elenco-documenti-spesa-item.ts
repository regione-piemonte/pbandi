/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ElencoDocumentiSpesaItem {
    constructor(
        public tipologia: string,
        public estremi: string,
        public fornitore: string,
        public importo: number,
        public validato: number,
        public stato: string,
        public progetto: string,
        public idDocumento: string,
        public importi: string,
        public progetti: string,
        public tipoInvio: string,
        public associabile: boolean,
        public clonabile: boolean,
        public eliminabile: boolean,
        public modificabile: boolean,
        public associato: boolean,
        public allegatiPresenti: boolean,
        public idProgetto: number,
        public associaButtonClicked?: boolean //solo frontend
    ) { }
}