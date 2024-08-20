/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EnteCompetenzaDTO } from "./ente-competenza-vo";
import { TipoProvvedimentoDTO } from "./tipo-provvedimento-vo";

export class ProvvedimentoDTO { 
    constructor(
        public idProvvedimento: number,
        public annoProvvedimento: number,
        public numeroProvvedimento: string,
        public tipoProvvedimento: TipoProvvedimentoDTO,
        public enteCompetenza: EnteCompetenzaDTO,
        public dataProvvedimento: Date,
        public enteDelegata: EnteCompetenzaDTO
    ) { }
}