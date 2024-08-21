/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ImportoAgevolato {
    constructor(
        public descrizione: string,
        public importo: number,
        public importoAlNettoRevoche: number
    ) { }
}