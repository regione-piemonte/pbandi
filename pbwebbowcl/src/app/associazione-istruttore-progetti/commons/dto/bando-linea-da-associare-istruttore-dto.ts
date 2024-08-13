/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoLineaDaAssociareAIstruttoreVO {
    constructor(
        public nomeBandolinea: string,
        public progBandoLinaIntervento: number,
        public idEnteDiCompetenza: number,
        public descBreveEnte: string
    ) { }
}