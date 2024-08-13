/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EconomiaPerDatiGeneraliDTO {
    constructor(
        public idProgettoCedente: number,
        public descBreveSoggFinanziatore: string,
        public impQuotaEconSoggFinanziat: number
    ) { }
}