/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PagamentoVoceDiSpesaDTO {
    constructor(
        public idVoceDiSpesa: number,
        public idQuotaParteDocSpesa: number,
        public idRigoContoEconomico: number,
        public voceDiSpesa: string,
        public importoRendicontato: number,
        public importoVoceDiSpesaCorrente: number,
        public totaleAltriPagamenti: number
    ) { }
}