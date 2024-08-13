/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CheclistDTO { 
    constructor(
        public idTipoDocumentoIndexArray: Array<number>,
        public idModelloArray: Array<number>,
        public idU: number,
        public progrBandoLineaIntervento: number
    ) { }
}