/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocPagamBandoLineaDTO { 
    constructor(
        public progrBandoLineaIntervento: number,
        public idTipoDocumento: number,
        public descTipoDocumento: string,
        public descBreveTipoDocumento: string,
        public idModalitaPagamento: number,
        public descModalitaPagamento: string,
        public descBreveModalitaPagamento: string,
        public progrEccezBanLinDocPag: number
    ) { }
}