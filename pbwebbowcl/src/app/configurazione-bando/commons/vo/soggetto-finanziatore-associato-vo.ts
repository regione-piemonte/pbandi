/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SoggettoFinanziatoreAssociatoDTO { 
    constructor(
        public idBando: number,
        public idSoggettoFinanziatore: number,
        public percentualeQuotaSoggFinanz: number,
        public idProvincia: number,
        public idComune: number,
        public descSoggFinanziatore: string,
        public descComune: string,
        public descProvincia: string,
        public descTabellare: string
    ) { }
}