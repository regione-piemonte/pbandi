/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AssociateFileRequest {
    constructor(
        public idProgetto: number,
        public idAppalto: number,
        public files: Array<string>,
        public idDocumentoIndexSelezionato: Array<string>       // array di idDocumentoIndex non usato
    ) { }
}