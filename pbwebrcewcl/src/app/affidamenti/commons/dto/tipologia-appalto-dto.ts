/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipologiaAppaltoDTO {
    constructor(
        public dtFineValidita: Date,
        public descBreveAppalto: string,
        public idTipologiaAppalto: number,
        public descTipologiaAppalto: string,
        public dtInizioValidita: Date
    ) { }
}