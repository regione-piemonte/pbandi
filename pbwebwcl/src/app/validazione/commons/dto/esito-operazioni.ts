/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoOperazioni {
    constructor(
        public esito: boolean,
        public msg: string,
        public params: Array<string>
    ) { }
}