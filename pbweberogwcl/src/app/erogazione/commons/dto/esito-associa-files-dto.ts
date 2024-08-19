/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoAssociaFilesDTO {
    constructor(
        public elencoIdDocIndexFilesAssociati: Array<number>,
        public elencoIdDocIndexFilesNonAssociati: Array<number>
    ) { }
}