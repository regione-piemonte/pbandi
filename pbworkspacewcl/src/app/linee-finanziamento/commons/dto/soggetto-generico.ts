/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SoggettoPF } from "./soggetto-pf";
import { SoggettoPG } from "./soggetto-pg";

export class SoggettoGenerico {
    constructor(
        public id: number,
        public datiPF: SoggettoPF,
        public datiPG: SoggettoPG,
        public flagPersonaFisica: string
    ) { }
}