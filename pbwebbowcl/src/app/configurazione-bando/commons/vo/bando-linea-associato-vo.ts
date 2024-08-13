/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoLineaAssociatoVo { 
    constructor(
        public progrBandoLineaIntervento: number,
        public nomeBandoLinea: string,
        public descBreveLinea: string
    ) { }
}