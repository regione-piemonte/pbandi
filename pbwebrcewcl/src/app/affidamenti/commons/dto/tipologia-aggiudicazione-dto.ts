/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipologiaAggiudicazioneDTO {
    constructor(
        public dtFineValidita: Date,
        public codIgrueT47: number,
        public dtInizioValidita: Date,
        public descTipologiaAggiudicazione: string,
        public idTipologiaAggiudicaz: number
    ) { }
}