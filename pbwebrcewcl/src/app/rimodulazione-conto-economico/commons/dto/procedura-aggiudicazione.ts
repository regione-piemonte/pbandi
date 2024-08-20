/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { StepAggiudicazione } from "./step-aggiudicazione";

export class ProceduraAggiudicazione {
    constructor(
        public idProceduraAggiudicaz: number,
        public descProcAgg: string,
        public importo: number,
        public iva: number,
        public codProcAgg: string,
        public cigProcAgg: string,
        public idTipologiaAggiudicaz: number,
        public descTipologiaAggiudicazione: string,
        public proceduraSelezionata: boolean,
        public codice: string,
        public linkModifica: string,
        public linkAssocia: string,
        public isModificabile: boolean,
        public iter: Array<StepAggiudicazione>
    ) { }
}