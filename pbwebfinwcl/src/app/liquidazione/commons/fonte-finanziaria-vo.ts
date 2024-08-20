/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FonteFinanziariaDTO { 
    constructor(
        public idFonteFinanziaria: number,
        public descFonteFinanziaria: string,
        public percentuale: number,
        public importo: number,
        public importoRispTotale: number,
        public percentualeRispTotale: number
    ) { }
}