/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VoceDiSpesaAssociataDTO { 
    constructor(
        public idVoceDiSpesa: number,
        public idVoceDiSpesaPadre: number,
        public descVoceDiSpesa: string,
        public idVoceDiSpesaMonit: number,
        public codTipoVoceDiSpesa: string,
        public progrOrdinamento: number,
        public idTipologiaVoceDiSpesa: number,
        public flagEdit: string,
        public flagSpeseGestione: string,
    ) { }
}