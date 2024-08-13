/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FileFirmato {

    file : File; 
    typeFirma : string;

    constructor(

        file : File, 
        typeFirma : string,

    ) { 

        this.file = file;
        this.typeFirma = typeFirma;
    }
}