/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContoEconomicoItem {
    constructor(
        public id: string,
        public idPadre: string,
        public haFigli: boolean,
        public type: string,
        public level: number,
        public label: string,
        public importoSpesaAmmessa: number,
        public importoSpesaRendicontata: number,
        public importoSpesaQuietanziata: number,
        public percentualeSpesaQuietanziataSuAmmessa: number,
        public importoSpesaValidataTotale: number,
        public percentualeSpesaValidataSuAmmessa: number,
        public idContoEconomico: number,
        public idVoce: number,
        public idVocePadre: number,
        public perc: number,                             //solo frontend
        public visible: boolean,                         //solo frontend
        public figliVisible: boolean                     //solo frontend
    ) { }
}