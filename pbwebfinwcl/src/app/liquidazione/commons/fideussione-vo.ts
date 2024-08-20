/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FideiussioneDTO { 
    constructor(
        public descrizione: string,
        public descrizioneTipoTrattamento: string,
        public dataDecorrenza: Date,
        public dataScadenza: Date,
        public numero: string,
        public importo: number,
        public importoTotaleTipoTrattamento: number,
        public id: number
    ) { }
}