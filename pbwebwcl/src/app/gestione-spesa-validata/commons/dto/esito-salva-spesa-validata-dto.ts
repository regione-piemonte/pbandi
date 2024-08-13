/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSalvaSpesaValidataDTO {
    constructor(
        public esito: boolean,
        public messaggi: Array<string>,
        public id: number,
        public chiedereConfermaPerProseguire: boolean
    ) { }
}