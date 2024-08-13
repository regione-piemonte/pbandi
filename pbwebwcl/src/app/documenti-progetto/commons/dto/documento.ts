/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Documento {
    constructor(
        public tipoDocumento: string,
        public nomeFile: string,
        public numeroProtocollo: string,
        public dataProtocollo: string,
        public dataMarcaturaTemporale: string,
        public dataVerificaFirma: string,
        public dataInserimento: string,
        public idDocumentoIndex: number,
        public idDomanda: number,
        public repository: string,
        public idAllegato: number,
        public fonte: string
    ) { }
}