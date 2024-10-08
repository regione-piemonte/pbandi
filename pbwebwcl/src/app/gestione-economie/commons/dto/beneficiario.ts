/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Beneficiario {
    constructor(
        public idSoggetto: number,
        public id_progetto: number,
        public codiceFiscale: string,
        public cognome: string,
        public nome: string,
        public descrizione: string,
        public id: number,
        public idFormaGiuridica: number,
        public idDimensioneImpresa: number
    ) { }
}