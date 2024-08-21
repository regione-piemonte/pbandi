/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiCumuloDeMinimis {
    constructor(
        public cumulo: string,
        public importoDisponibile: number,
        public importoSuperato: number
    ) { }
}