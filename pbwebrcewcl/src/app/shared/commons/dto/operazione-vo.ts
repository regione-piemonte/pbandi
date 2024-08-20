/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class OperazioneVO {
    constructor(
        public id: number,
        public descrizione: string,
        public selected?: boolean,
        public icona?: string,
        public isExpanded?: boolean
    ) { }
}