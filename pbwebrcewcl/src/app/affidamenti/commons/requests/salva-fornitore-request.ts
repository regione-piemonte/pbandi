/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SalvaFornitoreRequest {
    constructor(
        public idAppalto: number,
        public idFornitore: number,
        public idTipoPercettore: number
    ) { }
}