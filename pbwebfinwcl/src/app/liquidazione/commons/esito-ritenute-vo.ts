/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { RitenutaDTO } from "./ritenuta-vo";

export class EsitoRitenuteDTO {
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public ritenuta: RitenutaDTO
    ) { }
}