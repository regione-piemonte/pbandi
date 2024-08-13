/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class DatiBeneficiarioProgettoDTO{
    constructor(

        public serialVersionUID : number, 
        public cap : string,
        public civico : string,
        public codiceFiscale : string,
        public codiceVisualizzato : string,
        public denominazione : string,
        public email : string,
        public fax : string,
        public indirizzo : string,
        public idComune : number,
        public idEnteGiuridico : number,
        public idIndirizzo : number,
        public idProgetto : number,
        public idProvincia : number,
        public idRecapiti : number,
        public idRegione : number,
        public idSede : number,
        public idSoggetto : number,
        public partitaIva : string,
        public telefono : string,
        public message : string
        
    ){}


    public istanceCopy(data : DatiBeneficiarioProgettoDTO){

        this.serialVersionUID = data.serialVersionUID
        this.cap = data.cap
        this.civico = data.civico
        this.codiceFiscale = data.codiceFiscale
        this.codiceVisualizzato = data.codiceVisualizzato
        this.denominazione = data.denominazione
        this.email = data.email
        this.fax = data.fax
        this.indirizzo = data.indirizzo
        this.idComune = data.idComune
        this.idEnteGiuridico = data.idEnteGiuridico
        this.idIndirizzo = data.idIndirizzo
        this.idProgetto = data.idProgetto
        this.idProvincia = data.idProvincia
        this.idRecapiti = data.idRecapiti
        this.idRegione = data.idRegione
        this.idSede = data.idSede
        this.idSoggetto = data.idSoggetto
        this.partitaIva = data.partitaIva
        this.telefono = data.telefono
        this.message = data.message

    }

    public copy():DatiBeneficiarioProgettoDTO{

        var newObject  = new DatiBeneficiarioProgettoDTO (

            this.serialVersionUID,
            this.cap,
            this.civico,
            this.codiceFiscale,
            this.codiceVisualizzato,
            this.denominazione,
            this.email,
            this.fax,
            this.indirizzo,
            this.idComune,
            this.idEnteGiuridico,
            this.idIndirizzo,
            this.idProgetto,
            this.idProvincia,
            this.idRecapiti,
            this.idRegione,
            this.idSede,
            this.idSoggetto,
            this.partitaIva,
            this.telefono,
            this.message

        )
        
        return newObject

    }

}