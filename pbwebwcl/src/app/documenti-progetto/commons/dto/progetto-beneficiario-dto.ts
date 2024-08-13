/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class ProgettoBenenficiarioDTO {
    constructor(
        public codice: string,
        public descrizione: string,
        public numeroDomanda: string
    ) { }
}