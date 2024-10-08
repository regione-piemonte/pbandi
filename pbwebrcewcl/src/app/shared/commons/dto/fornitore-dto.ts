/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FornitoreDTO {
    constructor(
        public codiceFiscaleFornitore: string,
        public codQualificheNotIn: Array<string>,
        public codQualificheIn: Array<string>,
        public cognomeFornitore: string,
        public costoOrario: number,
        public costoRisorsa: number,
        public denominazioneFornitore: string,
        public descQualifica: string,
        public descBreveTipoSoggetto: string,
        public descTipoSoggetto: string,
        public dtFineValidita: Date,
        public flagHasQualifica: boolean,
        public idAttivitaAteco: number,
        public idFormaGiuridica: number,
        public idFornitore: number,
        public idNazione: number,
        public idQualifica: number,
        public idSoggettoFornitore: number,
        public idTipoSoggetto: number,
        public includiFornitoriInvalidi: boolean,
        public monteOre: number,
        public nomeFornitore: string,
        public partitaIvaFornitore: string,
        public altroCodice: string,
        public codUniIpa: string,
        public flagPubblicoPrivato: number
    ) { }
}