/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class Beneficiario{
    constructor(

        public serialVersionUID : number,        
        
        public id_soggetto : number,
        public id_progetto : number,
        public codiceFiscale : String,
        public cognome : String,
        public nome : String,
        public descrizione : String,
        public id : number,
        public idFormaGiuridica : number,
        public idDimensioneImpresa : number
        
        
    ){}
}