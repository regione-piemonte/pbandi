/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiBandoVo { 
    constructor(
        public idUtenteIns: number,
        public idUtenteAgg: number,
        public idBando: number,
        public titoloBando: string,
        public dataPubblicazione: Date,
        public dataPresentazioneDomande: Date,
        public dataScadenza: Date,
        public isPrevistaGraduatoria: boolean,
        public isPrevistoAllegato: boolean,
        public isQuietanza: boolean,
        public dotazioneFinanziaria: number,
        public idMateriaRiferimento: number,
        public costoTotaleMinimoAmmissibile: number,
        public percentualePremialita: number,
        public punteggioPremialita: number,
        public importoPremialita: number,
        public idCodiceIntesaIstituzionale: number,
        public idTipoOperazione: number,
        public idSettoreCIPE: number,
        public idSottoSettoreCIPE: number,
        public idNaturaCIPE: number,
        public idSettoreCPT: number,
        public idTipologiaAttivazione: number,
        public numMaxDomande: number,
        public flagFindom: string,
        public idLineaDiIntervento: number,
        public dataDeterminaApprovazione: Date,
        public determinaApprovazione: string,
        public isBandoCultura: boolean
    ) { }
}