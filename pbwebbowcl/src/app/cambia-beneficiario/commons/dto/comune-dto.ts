/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ComuneDTO {
    constructor(

        public seralVersionUID : number,

        public cap : string,
        public codIstatComune : string,
        public descrizione : string,   
        public idComune : number,     
        public idProvincia : number,
        
    ) { }
}