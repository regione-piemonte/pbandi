/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AliquotaRitenutaAttoDTO {
    constructor(
        public idAliquotaRitenuta: number,
        public codOnere: string,
        public codNaturaOnere: string,
        public descNaturaOnere: string,
        public percCaricoEnte: number,
        public percCaricoSoggetto: number,
        public importoCaricoEnte: number,
        public importoCaricoSoggetto: number
    ) { }
}