/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ResponseInizializzaIndicatori {
    constructor(
        public esisteCFP: boolean,
        public esisteDsFinale: boolean
    ) { }
}