/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RettificaVoceItem {
    constructor(
        public progrPagQuotParteDocSp: number,
        public descVoceDiSpesa: string,
        public dtRettifica: string,
        public importoRettifica: number,
        public riferimento: string,
        public codiceFiscaleSoggetto: string,
        public rigaVoce: boolean,
        public dtPagamento: string,
        public dtValuta: string,
        public importoPagamento: number,
        public modalitaPagamento: string,
        public dtPagamentoVisible: boolean,
        public dtValutaVisible: boolean
    ) { }
}