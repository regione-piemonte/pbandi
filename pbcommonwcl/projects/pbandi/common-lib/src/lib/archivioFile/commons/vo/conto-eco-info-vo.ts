/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContoEcoInfoVo {
    constructor(
        public codiceProgetto: string,
        public descBandoLinea: string,
        public dtProposta: string,
        public idContoEconomico: number,
        public titoloProgetto: string
    ) { }
}