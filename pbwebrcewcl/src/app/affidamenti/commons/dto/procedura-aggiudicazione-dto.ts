/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProceduraAggiudicazioneDTO {
    constructor(
        public idProceduraAggiudicaz: number,
        public idProgetto: number,
        public idTipologiaAggiudicaz: number,
        public idMotivoAssenzaCIG: number,
        public descProcAgg: string,
        public cigProcAgg: string,
        public codProcAgg: string,
        public importo: number,
        public iva: number,
        public dtAggiudicazione: Date,
        public dtInizioValidita: Date,
        public dtFineValidita: Date,
        public idUtenteIns: number,
        public idUtenteAgg: number
    ) { }
}