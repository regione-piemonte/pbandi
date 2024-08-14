/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StatoPropostaDTO {
    constructor(
        public idStatoPropostaCertif: number,
        public descStatoPropostaCertif: string,
        public descBreveStatoPropostaCert: string
    ) { }
}