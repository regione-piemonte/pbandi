/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SalvaVarianteRequest {
    constructor(
        public idTipologiaVariante: number,
        public importoVariante: number,
        public noteVariante: string,
        public idVariante: number,
        public idAppalto: number
    ) { }
}