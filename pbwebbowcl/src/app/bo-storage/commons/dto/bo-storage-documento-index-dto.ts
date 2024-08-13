/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BoStorageDocumentoIndexDTO {
    constructor(
        public idDocumentoIndex: number,
        public idProgetto: number,
        public codiceVisualizzatoProgetto: string,
        public idTipoDocIndex: number,
        public descrizioneBreveTipoDocIndex: string,
        public descrizioneTipoDocIndex: string,
        public nomeFile: string,
        public flagFirmato: boolean,
        public flagMarcato: boolean,
        public flagFirmaAutografa: boolean
    ) { }
}