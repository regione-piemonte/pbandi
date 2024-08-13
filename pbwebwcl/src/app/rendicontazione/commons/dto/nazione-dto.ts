/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class NazioneDTO {
    constructor(
        public idNazione: number,
        public codIstatNazione: string,
        public descNazione: string,
        public flagAppartenenzaUe: string
    ) { }
}