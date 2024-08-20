/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StepAggiudicazione {
    constructor(
        public id: string,
        public idStepAggiudicazione: number,
        public descStepAggiudicazione: string,
        public dtPrevista: Date,
        public dtEffettiva: Date,
        public importo: number,
        public idMotivoScostamento: number,
        public label: string
    ) { }
}