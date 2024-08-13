/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoChecklistAffidamentoDTO {
    constructor(
        public esito: string,
        public fase: number,
        public flagRettifica: string,
        public idChecklist: number,
        public idEsito: number,
        public note: string
    ) { }
}