/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InfoCreaAttoDTO { 
    constructor(
        public descCausale: string,
        public descSettore: string,
        public noteAtto: string,
        public importoAtto: number,
        public descEnte: string
    ) { }
}