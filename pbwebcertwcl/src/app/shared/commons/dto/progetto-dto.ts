/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoDTO {
    constructor(
        public idProgetto: number,
        public codiceVisualizzato: string,
        public codiceProgetto: string,
        public titoloProgetto: string,
        public cup: string
    ) { }
}