/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Decodifica { 
    constructor(
        public id: number,
        public descrizione: string,
        public descrizioneBreve: string,
        public dataInizioValidita: Date,
        public dataFineValidita: Date
    ) { }
}