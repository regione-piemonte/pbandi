/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AttoDiLiquidazioneVo { 
    constructor(
        public idAttoLiquidazione: number,
        public annoAtto: string,
        public numeroAtto: string,
        public idSettoreEnte: number,
        public estremiAtto: string,
        public descStatoAtto: string,
        public annoEsercizioImpegno: string,
        public annoImpegno: string,
        public codiceVisualizzatoProgetto: string,
        public denominazioneBeneficiarioBil: string,
        public idBeneficiarioBilancio: number,
        public idProgetto: number,
        public numeroImpegno: string,
        public impegno: string,
        public descBreveStatoAtto: string,
        public idSoggetto: number
    ) { }
}