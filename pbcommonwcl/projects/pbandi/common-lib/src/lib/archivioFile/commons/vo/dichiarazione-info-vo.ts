/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DichiarazioneInfoVo {
    constructor(
        public codiceProgetto: string,
        public descBandoLinea: string,
        public descTipoDichiarazioneSpesa: string,
        public dtDichiarazione: string,
        public dtInserimento: string,
        public idDichiarazioneSpesa: number,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idSoggettoBen: number,
        public idTarget: number,
        public nomeFile: string,
        public sizeFile: number,
        public titoloProgetto: string,
    ) { }
}