/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AgevolazioneRow {
    constructor(
        public id: number,
        public descrizione: string,
        public importoAgevolato: number,
        public importoErogato: number,
        public importoRecuperato: number,
        public data: Date,
        public riferimento: string,
        public isParent: boolean,
        public importoNuovo?: number,
        public importoNuovoFormatted?: string
    ) { }
}