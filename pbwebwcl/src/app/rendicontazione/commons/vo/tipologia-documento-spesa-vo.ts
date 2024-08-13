/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipologiaDocumentoSpesaVo { 
    constructor(
        public progrBandoLineaIntervento: number,
        public idTipoDocumentoSpesa: number,
        public descBreveTipoDocSpesa: string,
        public descTipoDocumentoSpesa: string
    ) { }
}