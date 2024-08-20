/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ChecklistAffidamentoHtmlBozzaDTO } from "../dto/checklist-affidamentohtml-bozza-dto";
import {ChecklistAffidamentoHtmlDTO} from "../dto/checklist-affidamentohtml-dto"

export class SalvaCheckListAffidamentoDocumentaleBozzaHtmlRequest{
    constructor(
        public idProgetto: number,
        public codStatoTipoDocIndex: string,
        public checklistHtmlBozzaDTO: ChecklistAffidamentoHtmlBozzaDTO,
        public idAffidamento: number,
        public isRichiestaIntegrazione: boolean,
        public noteRichiestaIntegrazione: string,
        public codTipoChecklist: string
    ){};
}