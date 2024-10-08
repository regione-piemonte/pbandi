/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FornitoreInfoVo {
    constructor(
        public cfFornitore: string,
        public codiceProgetto: string,
        public descBandoLinea: string,
        public dtInserimento: string,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idSoggettoBen: number,
        public idTarget: number,
        public nomeFile: string,
        public nomeFornitore: string,
        public sizeFile: number,
        public titoloProgetto: string
    ) { }
}