/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BeneficiarioDTO {
    constructor(
        public cognome: string,
        public nome: string,
        public codiceFiscale: string,
        public idSoggetto: number,
        public descrizione: string,
        public dataInizioValidita: Date,
        public denominazioneBeneficiario: string
    ) { }
}