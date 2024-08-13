/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MessaggioDTO } from "./messaggio-DTO";

export class EsitoAnteprimaTemplateDTO { 
    constructor(
        public esito: boolean,
        public bytes: string,
        public nomeFile: string,
        public msg: MessaggioDTO
    ) { }
}