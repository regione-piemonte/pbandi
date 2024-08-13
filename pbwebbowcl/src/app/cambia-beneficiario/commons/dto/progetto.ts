/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Progetto {
    constructor(

        public seralVersionUID : number,

        public idProgetto : number,
        public codiceVisualizzatoProgetto : string,
        public cup : string
        
    ) { }
}