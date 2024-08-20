/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContoEconomicoItem {
    constructor(
        public id: string,
        public idPadre: string,
        public idRigoContoEconomico: number,
        public haFigli: boolean,
        public type: string,
        public level: number,
        public label: string,
        public importoRichiestoInDomanda: string,
        public importoRichiestoUltimaProposta: string,
        public importoRichiestoNuovaProposta: string,
        public importoSpesaAmmessaInDetermina: string,
        public importoSpesaAmmessaUltima: string,
        public importoSpesaAmmessaRimodulazione: string,
        public importoSpesaRendicontata: string,
        public importoSpesaQuietanziata: string,
        public percSpesaQuietanziataSuAmmessa: string,
        public importoSpesaValidataTotale: string,
        public percSpesaValidataSuAmmessa: string,
        public modificabile: boolean,
        public idVoce: number,
        public codiceErrore: string,
        public dataUltimaProposta: string,
        public dataFineIstruttoria: string,
        public dataPresentazioneDomanda: string,
        public dataUltimaRimodulazione: string,
        public perc: number,                             //solo frontend
        public visible: boolean,                         //solo frontend
        public figliVisible: boolean
    ) { }
}