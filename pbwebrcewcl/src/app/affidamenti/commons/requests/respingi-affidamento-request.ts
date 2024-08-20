/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RespingiAffidamentoRequest {
    constructor(
        public idAppalto: number,
        public note: string
    ) { }
}