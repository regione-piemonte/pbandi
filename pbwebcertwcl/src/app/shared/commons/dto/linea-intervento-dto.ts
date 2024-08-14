/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class LineaInterventoDTO {
    constructor(
        public idLineaDiIntervento: number,
        public descBreveLinea: string,
        public descLinea: string,
    ) { }
}