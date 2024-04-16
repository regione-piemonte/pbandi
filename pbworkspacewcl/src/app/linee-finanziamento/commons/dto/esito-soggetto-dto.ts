/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SoggettoPGDTO } from "./soggetto-pg-dto";

export class EsitoSoggettoDTO {
    constructor(
        public datiPG: SoggettoPGDTO,
        public esito: boolean,
        public erroriCampi: Array<string>,
        public chiaviCampi: Array<string>
    ) { }
}