/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SchedaProgetto } from "../dto/scheda-progetto";

export class SalvaSchedaProgettoRequest {
    constructor(
        public schedaProgetto: SchedaProgetto,
        public datiCompletiPerAvvio: boolean
    ) { }
}