/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AvvisoUtenteDTO {
    constructor(
        public idNews: number,
        public titolo: string,
        public descrizione: string,
        public tipoNews: string,
        public dtInizio: Date
    ) { }
}