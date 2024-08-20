/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiIntegrativiDTO { 
    constructor(
        public dtScadenzaAtto: Date,
        public descAtto: string,
        public flagAllegatiFatture: string,
        public flagAllegatiEstrattoProv: string,
        public flagAllegatiDocGiustificat: string,
        public flagAllegatiDichiarazione: string,
        public idAttoLiquidazione: number,
        public idSettoreEnte: number,
        public noteAtto: string,
        public nomeDirigenteLiquidatore: string,
        public nomeLiquidatore: string,
        public numeroTelefonoLiquidatore: string,
        public testoAllegatiAltro: string,
    ) { }
}