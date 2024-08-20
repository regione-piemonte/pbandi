/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SedeBilancioDTO { 
    constructor(
        public indirizzo: string,
        public cap: string,
        public descComune: string,
        public descProvincia: string
    ) { }
}