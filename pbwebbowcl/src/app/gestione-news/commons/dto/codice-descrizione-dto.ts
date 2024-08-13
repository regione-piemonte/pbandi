/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CodiceDescrizioneDTO {
    constructor(
        public id: number,
        public descrizione: string,
        public descrizioneBreve: string
    ) { }
}