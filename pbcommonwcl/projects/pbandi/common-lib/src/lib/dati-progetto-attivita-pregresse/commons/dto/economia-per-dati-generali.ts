/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EconomiaPerDatiGenerali {
    constructor(
        public descBreveSoggFinanziatore: string,
        public idProgettoCedente: number,
        public impQuotaEconSoggFinanziat: number
    ) { }
}