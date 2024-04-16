/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Progetto } from "../dto/progetto";

export class AvviaProgettiRequest {
    constructor(
        public progrBandoLineaIntervento: number,
        public progettiDaAvviare: Array<Progetto>,
        public idSoggetto: number,
        public codiceRuolo: string
    ) { }
}