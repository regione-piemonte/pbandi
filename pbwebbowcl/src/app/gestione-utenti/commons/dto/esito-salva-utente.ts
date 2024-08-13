/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSalvaUtente {
    constructor(

        public seralVersionUID : number,

        public esito : boolean,
        public message : string,
        public params : Array<string>,
        public idSoggetto : number,
        public idPersonaFisica : number,
        public idTipoAnagrafica : number,
        
        
    ) { }
}