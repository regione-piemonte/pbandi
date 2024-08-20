/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import {ChecklistAffidamentoHtmlDTO} from "../dto/checklist-affidamentohtml-dto"

export class SalvaCheckListAffidamentoDocumentaleHtmlRequest{
    constructor(
        public idProgetto: number,
        public codStatoTipoDocIndex: string,
        public checklistHtmlDTO: ChecklistAffidamentoHtmlDTO,
        public idAffidamento: number,
        public isRichiestaIntegrazione: boolean,
        public noteRichiestaIntegrazione: string,
        public codTipoChecklist: string
    ){};
}