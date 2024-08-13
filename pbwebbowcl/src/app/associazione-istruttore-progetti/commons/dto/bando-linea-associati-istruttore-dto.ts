/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoLineaAssociatiAIstruttoreVO {
    constructor(
        public nomeBandolinea: string,
        public progBandoLina: number,
        public numIstruttoriAssociati: number,
        public nomneIstruttore: string,
        public cognomeIstruttore: string
    ) { }
}