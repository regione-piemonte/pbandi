/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentiSpesaSospesiVO {
    constructor(

        public idDocuSpesa: number,
        public importoRendicon: string,
        public idUtenteIns: string,
        public idUtenteAgg: string,
        public noteValidazione: string,
        public task: string,
        public idStatoDocumentoSpesa: string,
        public idStatoDocumentoSpesaValid:string,
        public tipoInvio: string,
        public idAppalto: string
        
        ){}
}
