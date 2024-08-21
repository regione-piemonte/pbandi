/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ResponseCodeMessage {
    constructor(
        public code: string,
        public message: string
    ) { }
}

export class EsitoAssociaFilesDTO {
    constructor(
        public elencoIdDocIndexFilesAssociati: Array<number>,
        public elencoIdDocIndexFilesNonAssociati: Array<number>
    ) { }
}

export class ResponseCodeMessageAllegati extends ResponseCodeMessage {
    public esitoAssociaFilesDTO: EsitoAssociaFilesDTO;
}