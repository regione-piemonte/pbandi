/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Progetto {
    constructor(
        public id: number,
        public codice: string,
        public titolo: string,
        public importoAgevolato: number,
        public cup: string,
        public acronimo: string,
        public dtConcessione: Date
    ) { }
}