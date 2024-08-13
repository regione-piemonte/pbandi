/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoDTO {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public id: number
    ) { }
}