/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizione } from "./codice-descrizione";
import { SchedaProgetto } from "./scheda-progetto";

export class InizializzaSchedaProgettoDTO {
    constructor(
        public nomeBandoLinea: string,
        public idProcesso: number,
        public comboPrioritaQsn: Array<CodiceDescrizione>,
        public schedaProgetto: SchedaProgetto,
        public isProgrammazione2127: boolean
    ) { }
}