/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AssociaFilesRequest {
    constructor(
        public elencoIdDocumentoIndex: Array<number>,
        public idTarget: number,
        public idProgetto: number
    ) { }
}