/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoScaricaDocumentoDTO {
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public bytesDocumento: any,
        public nomeFile: string
    ) { }
}