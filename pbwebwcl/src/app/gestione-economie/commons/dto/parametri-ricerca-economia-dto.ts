/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ParametriRicercaEconomieDTO {
    constructor(
        public filtro: string,
        public beneficiario: string,
        public progetto: string,
        public beneficiarioRicevente: string,
        public progettoRicevente: string
    ) { }
}