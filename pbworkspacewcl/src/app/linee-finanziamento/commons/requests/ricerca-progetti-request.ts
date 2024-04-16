/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RicercaProgettiRequest {
    constructor(
        public progrBandoLineaIntervento: number,
        public codiceProgetto: string,
        public titoloProgetto: string,
        public cup: string,
        public beneficiario: string,
        public idSoggetto: number,
        public codiceRuolo: string
    ) { }
}