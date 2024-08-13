/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AppaltoProgetto {
    constructor(
        public idProgetto: number,
        public idAppalto: number,
        public associato: boolean,
        public descrizione: string
    ) { }
}