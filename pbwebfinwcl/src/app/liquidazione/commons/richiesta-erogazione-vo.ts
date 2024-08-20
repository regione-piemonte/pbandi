/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RichiestaErogazioneDTO { 
    constructor(
        public descCausaleErogazione: string,
        public numeroRichiestaErogazione: string,
        public dataRichiestaErogazione: Date,
        public importoRichiestaErogazione: number,
    ) { }
}