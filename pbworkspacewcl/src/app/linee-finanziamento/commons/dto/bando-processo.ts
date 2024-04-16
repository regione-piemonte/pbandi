/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoProcesso {
    constructor(
        public id: string,
        public nome: string,
        public versione: string,
        public processo: string
    ) { }
}