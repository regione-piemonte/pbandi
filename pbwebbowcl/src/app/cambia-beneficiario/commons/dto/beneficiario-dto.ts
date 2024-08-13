/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BeneficiarioDTO {
    constructor(

        public anagraficaAggiornabile : boolean,
        public codiceFiscaleSoggetto : String,
        public denominazioneBeneficiario : String,
        public idEnteGiuridico : number,
        public idProgetto : number,
        public idSoggetto : number,
        public titoloProgetto : String        
        
    ){}
}


