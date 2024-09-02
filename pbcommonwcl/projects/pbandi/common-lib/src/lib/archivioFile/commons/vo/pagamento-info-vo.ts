/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PagamentoInfoVo {
    constructor(
        public cfFornitore: string,
        public codiceProgetto: string,
        public descBandoLinea: string,
        public descModalitaPagamento: string,
        public descTipoDocumentoSpesa: string,
        public dtEmissioneDocumento: string,
        public dtEmissioneDocumentoFormattata: string,
        public dtInserimento: string,
        public dtPagamento: string,
        public dtPagamentoFormattata: string,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idModalitaPagamento: number,
        public idSoggettoBen: number,
        public idTarget: number,
        public importoPagamento: number,
        public nomeFile: string,
        public nomeFornitore: string,
        public numeroDocumento: string,
        public sizeFile: number,
        public titoloProgetto: string,
    ) { }
}