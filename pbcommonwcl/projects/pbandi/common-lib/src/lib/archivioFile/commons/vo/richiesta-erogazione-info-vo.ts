/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RichiestaErogazioneInfoVo {
    constructor(
        public codiceProgetto: string,
        public descBandoLinea: string,
        public dtRichiestaErogazione: string,
        public idRichiestaErogazione: number,
        public importoErogazioneRichiesto: number,
        public numeroRichiestaErogazione: string,
        public titoloProgetto: string
    ) { }
}