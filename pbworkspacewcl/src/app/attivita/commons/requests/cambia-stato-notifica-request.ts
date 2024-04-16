/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CambiaStatoNotificaRequest {
    constructor(
        public idNotifica: number,
        public statoNotifica: string,
        public idNotificheArray: Array<number>
    ) { }
}