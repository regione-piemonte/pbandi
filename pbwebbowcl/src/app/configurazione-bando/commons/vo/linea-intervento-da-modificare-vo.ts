/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class LineaInterventoDaModificareVo {
    constructor(
        public idBando: number,
        public idNormativa: number,
        public idAsse: number,
        public idMisura: number,
        public idLinea: number,
        public nomeBandoLinea: string,
        public idDefinizioneProcesso: number,
        public idPrioritaQSN: number,
        public idObiettivoGeneraleQSN: number,
        public idObiettivoSpecifQSN: number,
        public idCategoriaCIPE: number,
        public idTipologiaCIPE: number,
        public mesiDurataDtConcessione: number,
        public idLineaDiIntervento: number,
        public progrBandoLineaIntervento: number,
        public idProcesso: number,
        public idUtenteIns: number,
        public idUtenteAgg: number,
        public idAreaScientifica: number,
        public idUnitaOrganizzativa: number,
        public flagSchedin: string,
        public flagSif: string,
        public progrBandoLineaIntervSif: number,
        public nomeBandoLineaSif: string,
        public idTipoLocalizzazione: number,
        public idLivelloIstituzione: number,
        public idProgettoComplesso: number,
        public idClassificazioneMet: number,
        public flagFondoDiFondi: string,
        public idClassificazioneRa: number,
        public idObiettivoTem: number,
        public codAiutoRna: string,
        public flagArt58: string
    ) { }
}