/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ModelloDTO {
    constructor(
        public idU: number,
        public progrBandoLineaInterventoOld: number,
        public progrBandoLineaInterventoNew: number,
        public elencoIdTipoDocumentoIndex: Array<number>,
        public elencoIdTipoDocumentoIndexAssociato: Array<number>
    ) { }
}