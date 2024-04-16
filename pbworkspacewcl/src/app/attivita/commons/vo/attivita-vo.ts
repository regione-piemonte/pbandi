/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AttivitaVO {
    constructor(
        public acronimoProgetto: string,
        public codiceVisualizzato: string,
        public denominazioneLock: string,
        public descrBreveTask: string,
        public descrTask: string,
        public flagOpt: string,
        public flagLock: string,
        public idBusiness: number,
        public idNotifica: number,
        public idProgetto: number,
        public nomeBandoLinea: string,
        public progrBandoLineaIntervento: number,
        public titoloProgetto: string,
        public flagUpdRecapito: string,
        public flagRichAbilitazUtente: string
    ) { }
}