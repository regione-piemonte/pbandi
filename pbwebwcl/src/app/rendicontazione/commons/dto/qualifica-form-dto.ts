/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class QualificaFormDTO {
    constructor(
        public progrFornitoreQualifica: number,
        public idFornitore: number,
        public idQualifica: number,
        public costoOrario: number,
        public noteQualifica: string
    ) { }
}