/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PagamentoFormDTO {
    constructor(
        public idPagamento: number,
        public idModalitaPagamento: number,
        public dtPagamento: Date,
        public importoPagamento: number,
        public idCausalePagamento: number,
        public flagPagamento: string,
        public rifPagamento: string
    ) { }
}