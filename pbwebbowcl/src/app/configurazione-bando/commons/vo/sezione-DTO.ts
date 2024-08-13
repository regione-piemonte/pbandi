/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SezioneVO { 
    constructor(
        public progrBandoLineaIntervento: number,
        public idTipoDocumentoIndex: number,
        public numOrdinamentoMacroSezione: number,
        public idMacroSezioneModulo: number,
        public numOrdinamentoMicroSezione: number,
        public idMicroSezioneModulo: number,
        public progrBlTipoDocSezMod: number,
        public contenutoMicroSezione: string,
        public descMicroSezione: string,
        public descMacroSezione: string
    ) { }
}