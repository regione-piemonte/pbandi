/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CostantiBandoLineaDTO { 
    constructor(
        public normaIncentivazione: string,
        public codiceNormativa: string,
        public idTipoOperazione: number,
        public idStatoDomanda: number,
        public progrBandoLineaIntervento: number,
        public statoInvioDomanda: number,
        public statoProgetto: number,
        public statoContoEconomico: number
    ) { }
}