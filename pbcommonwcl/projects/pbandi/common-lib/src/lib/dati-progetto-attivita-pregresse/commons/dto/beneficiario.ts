/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Beneficiario {
    constructor(
        public idBeneficiario: number,
        public nome: string,
        public cognome: string,
        public idCombo: number,
        public descrizione: string,
        public codiceFiscale: string,
        public sede: string,
        public idSoggetto: number,
        public idFormaGiuridica: number,
        public idDimensioneImpresa: number,
        public isCapofila: boolean,
        public sedeLegale: string,
        public pIvaSedeLegale: string
    ) { }
}