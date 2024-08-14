/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AggiornaStatoRequest {
    constructor(
        public idProposta: number,
        public idStatoNuovo: number,
        public idUtente: number
    ) { }
}