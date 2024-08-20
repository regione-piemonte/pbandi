/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ControdeduzioniVO {

    constructor(
        public  idGestioneRevoca: number,
        public  numeroRevoca: number,
        public  dataNotifica: Date,
        public  numeroProtocollo: string,
        public  causaRevoca: string,
        
        public  idControdeduzione: number,
        public  numeroControdeduzione: number,
        public  idStatoControdeduzione: number,
        public  descStatoControdeduzione: string,
        public  dtStatoControdeduzione: Date,
        public  idAttivitaControdeduzione: number,
        public  descAttivitaControdeduzione: string,
        public  dtAttivitaControdeduzione: Date,
        public  dtScadenzaControdeduzione: Date,
        
        public  isAbilitatoIntegra: boolean,
        public  isAbilitatoInvia: boolean,
        public  isAbilitatoElimina: boolean,
        public  isAbilitatoAtti: boolean,
        public  isAbilitatoControdeduz: boolean,
        
        public  idStatoProroga: number,
        public  descStatoProroga: string,
    ) { }
}