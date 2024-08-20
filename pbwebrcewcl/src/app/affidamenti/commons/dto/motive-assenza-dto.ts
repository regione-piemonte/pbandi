/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class MotiveAssenzaDTO {
    constructor(
        public idMotivoAssenzaCig: number,
        public dtFineValidita: Date,
        public dtInizioValidita: Date,
        public descBreveMotivoAssenzaCig: string,
        public descMotivoAssenzaCig: string,
        public tc22: string
    ) { }
}