/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Affidamento } from "../dto/affidamento";
import { ProceduraAggiudicazioneAffidamento } from "../dto/procedura-aggiudicazione-affidamento";

export class SalvaAffidamentoRequest {
    constructor(
        public affidamento: Affidamento,
        public procAgg: ProceduraAggiudicazioneAffidamento
    ) { }
}