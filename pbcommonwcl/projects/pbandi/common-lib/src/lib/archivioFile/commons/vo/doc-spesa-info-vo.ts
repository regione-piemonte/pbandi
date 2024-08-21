/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocSpesaInfoVo {
    constructor(
        public cfFornitore: string,
        public codiceProgetto: string,
        public descBandoLinea: string,
        public descBreveStatoDocSpesa: string,
        public descStatoDocumentoSpesa: string,
        public descTipoDocumentoSpesa: string,
        public dtEmissioneDocumento: string,
        public dtEmissioneDocumentoFormattata: string,
        public dtInserimento: string,
        public idDocumentoDiSpesa: number,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idSoggettoBen: number,
        public idStatoDocumentoSpesa: number,
        public idTarget: number,
        public idTipoDocumentoSpesa: number,
        public nomeFile: string,
        public nomeFornitore: string,
        public numeroDocumento: string,
        public sizeFile: number,
        public titoloProgetto: string,
    ) { }
}