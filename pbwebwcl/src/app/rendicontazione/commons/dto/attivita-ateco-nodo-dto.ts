/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AttivitaAtecoNodoDTO {
    constructor(
        public idAttivitaAteco: number,
        public codAteco: string,
        public codDescAteco: string,
        public figli: Array<AttivitaAtecoNodoDTO>
    ) { }
}