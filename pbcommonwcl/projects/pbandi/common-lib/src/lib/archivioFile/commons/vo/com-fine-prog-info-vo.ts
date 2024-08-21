/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ComFineProgInfoVo {
    constructor(
        public codiceProgetto: string,
        public descBandoLinea: string,
        public dtComunicazione: string,
        public idComunicazione: number,
        public titoloProgetto: string,
    ) { }
}