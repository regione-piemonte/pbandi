/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InfoCreaAttoDTO } from "./info-crea-atto-vo"

export class EsitoInfoCreaAttoDTO { 
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public infoCreaAtto: InfoCreaAttoDTO
    ) { }
}