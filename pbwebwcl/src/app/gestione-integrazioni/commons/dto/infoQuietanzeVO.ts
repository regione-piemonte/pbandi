/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class infoQuietanzeVO {

    constructor(
        public importoPagamento?: number,
        public idPagamento?: number,
        public idModalitaPagamento?: number,
        public descModalitaPagamento?: string,
        public dataPagamento?: Date,
        public dataInvio ?: number,
    ) { }
}