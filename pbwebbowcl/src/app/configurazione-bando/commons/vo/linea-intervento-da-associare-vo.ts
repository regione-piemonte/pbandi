/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class LineaInterventoDaAssociareVo {
    constructor(
        public idBando: number,
        public idLineaDiIntervento: number,
        public nomeBandoLinea: string,
        public idDefinizioneProcesso: number,
        public idCategoriaCIPE: number,
        public idTipologiaCIPE: number,
        public idObiettivoSpecifQSN: number,
        public mesiDurataDtConcessione: number,
        public idProcesso: number,
        public flagSif: string,
        public progrBandoLineaIntervSif: number,
        public idTipoLocalizzazione: number,
        public idLivelloIstituzione: number,
        public idProgettoComplesso: number,
        public idClassificazioneMet: number,
        public flagFondoDiFondi: string,
        public idClassificazioneRa: number,
        public codAiutoRna: string,
        public flagArt58: string
    ) { }
}