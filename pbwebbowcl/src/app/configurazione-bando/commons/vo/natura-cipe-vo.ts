/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class NaturaCipeVO {
    constructor(
        public idNaturaCipe: number,
        public codNaturaCipe: string,
        public descNaturaCipe: string,
        public dtInizioValidita: Date,
        public dtFineValidita: Date,
        public idTipoOperazione: number
    ) { }
}