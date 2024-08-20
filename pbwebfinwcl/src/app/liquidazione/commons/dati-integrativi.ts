/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EnteCompetenzaDTO } from "src/app/gestione-disimpegni/commons/ente-competenza-vo";
import { DatiIntegrativiDTO } from "./dati-integrativi-vo";
import { SettoreEnteDTO } from "./settore-ente-vo";

export class DatiIntegrativi {
    constructor(
        public causaleDiPagamento: string,
        public idEnteCompetenza: number,
        public idSettoreDiAppartenenza: number,
        public listEnteDiCompetenza: Array<EnteCompetenzaDTO>,
        public listSettoreDiAppartenenza: Array<SettoreEnteDTO>,
        public datiIntegrativi: DatiIntegrativiDTO
    ) { }
}