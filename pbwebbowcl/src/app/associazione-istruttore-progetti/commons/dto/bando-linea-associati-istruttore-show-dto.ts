/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BandoLineaAssociatiAIstruttoreVO } from "./bando-linea-associati-istruttore-dto";

export class BandoLineaAssociatiAIstruttoreShowVO {
    constructor(
        public nomeBandolinea: string,
        public progBandoLina: number,
        public numIstruttoriAssociati: number,
        public nomneIstruttore: string,
        public cognomeIstruttore: string,
        public showIstrAssoc: boolean,
        public istrAssociati: Array<BandoLineaAssociatiAIstruttoreVO>
    ) { }
}