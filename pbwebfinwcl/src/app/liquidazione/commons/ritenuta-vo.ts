/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RitenutaDTO { 
    constructor(
        public idAttoLiquidazione: number,
        public sommeNonImponibili: number,
        public idTipoSoggettoRitenuta: number,
        public idTipoRitenuta: number,
        public idAliquotaRitenuta: number,
        public idSituazioneInps: number,
        public idRitenutaPrevidenziale: number,
        public idAltraCassaPrevidenziale: number,
        public idAttivitaInps: number,
        public dtInpsDal: Date,
        public dtInpsAl: Date,
        public idRischioInail: number,
        public imponibile: number
    ) { }
}