/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PbandiTNotificaProcessoVO {
    constructor(
        public idNotifica: number,
        public idProgetto: number,
        public idRuoloDiProcessoDest: number,
        public subjectNotifica: string,
        public messageNotifica: string,
        public statoNotifica: string, //I: da leggere, R: letta, C: archiviata
        public idUtenteMitt: number,
        public dtNotifica: Date,
        public idUtenteAgg: number,
        public dtAggStatoNotifica: Date,
        public idTemplateNotifica: number,
        public idEntita: number,
        public idTarget: number,
        public codiceVisualizzatoProgetto?: string,
        public checked?: boolean
    ) { }
}