/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SettoreEnteDTO { 
    constructor(
        public descSettore: string,
        public idSettoreEnte: number,
        public idEnteCompetenza: number
    ) { }
}