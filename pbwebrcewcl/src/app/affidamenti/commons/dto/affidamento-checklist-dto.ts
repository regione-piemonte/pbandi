/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AffidamentoCheckListDTO {
    constructor(
        public idAffidamentoChecklist: number,
        public idNorma: number,
        public idTipoAffidamento: number,
        public idTipoAppalto: number,
        public idTipologiaAggiudicaz: number,
        public sopraSoglia: string,
        public idModelloCd: number,
        public idModelloCl: number,
        public rifChecklistAffidamenti: string,
        public idLineaDiIntervento: number
    ) { }
}