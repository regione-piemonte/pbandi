/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Decodifica {

    public id : number
    public descrizione : string
    public descrizioneBreve : string
    public dataInizioValidita : Date
    public dataFineValidita : Date
    public hasPeriodoDiStabilita : boolean

    constructor(
        id : number,
        descrizione : string,
        descrizioneBreve : string,
        dataInizioValidita : Date,
        dataFineValidita : Date, 
        hasPeriodoDiStabilita : boolean = false       
    ) {
        this.id = id
        this.descrizione = descrizione
        this.descrizioneBreve = descrizioneBreve
        this.dataInizioValidita = dataInizioValidita
        this.dataFineValidita = dataFineValidita
        this.hasPeriodoDiStabilita = hasPeriodoDiStabilita
     }



     
}