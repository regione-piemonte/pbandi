/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DettaglioLiquidazioneVo { 
    constructor(
        public numBilancioLiquidazione: string,
        public importoLiquidato: number,
        public cupLiquidazione: string,
        public cigLiquidazione: string,
        public dtInserimento: Date,
        public dtAggiornamento: Date,
        public dtAggBilancioLiquidaz: Date,
        public annoImpegno: string,
        public numeroImpegno: string,
        public annoEsercizio: string,
        public cupImpegno: string,
        public cigImpegno: string,
        public totaleLiquidatoImpegno: number,
        public totaleQuietanzatoImpegno: number,
        public descTipoFondo: string,
        public numeroCapitolo: string,
        public numeroMandato: string,
        public importoMandatoLordo: number,
        public importoRitenute: number,
        public importoMandatoNetto: number,
        public importoQuietanzato: number,
        public dtQuietanzaMandato: Date,
        public cupMandato: string,
        public cigMandato: string,
        public dtInserimentoMandato: Date,
        public idLiquidazione: number,
        public annoLiquidazione: string,
        public statoLiquidazione: string,
    ) { }
}