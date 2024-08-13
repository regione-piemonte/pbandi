/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroRicercaDocumentiSpesa {
    constructor(
        public partner: string,
        public documentiDiSpesa: string,
        public idTipologia: number,
        public idStato: number,
        public idFornitore: number,
        public idVoceDiSpesa: number,
        public numero: string,
        public data: Date,
        public dataA: Date,
        public task: string,
        public idCategoria?: number,
    ) { }
}