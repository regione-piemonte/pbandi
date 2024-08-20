/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RipartizioneImpegniLiquidazioneDTO {
    constructor(
        public idFonteFinanziaria: number,
        public idImpegno: number,
        public descFonteFinanziaria: string,
        public descCausale: string,
        public capitoloNumero: string,
        public impegnoAnnoNumero: string,
        public importoImpegno: number,
        public disponibilitaResidua: number,
        public importoDaLiquidare: number,
        public cig: string,
        public annoEsercizio: number,
        public annoImpegno: number,
        public numeroImpegno: number,
        public cup: string,
        public tipoProv: string,
        public nroProv: string,
        public annoProv: string,
        public direzione: string,
        public annoPerente: number,
        public numeroPerente: number,
        public impegnoPerenteAnnoNumero: string,
        public importoDaLiquidareFormatted: string, //solo frontend
    ) { }
}