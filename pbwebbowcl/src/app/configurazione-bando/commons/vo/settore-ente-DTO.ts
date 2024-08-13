/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SettoreEnteDTO { 
    constructor(
        public descSettore: string,
        public descBreveSettore: string,
        public idEnteCompetenza: number,
        public idSettoreEnte: number,
        public idIndirizzo: number
    ) { }
}