/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class NotificaAlertVO {
    constructor(
        public descrNotifica: string,
        public dtFineValidita: Date,
        public hasProgettiAssociated: string,
        public idFrequenza: number,
        public idNotificaAlert: number,
        public idProgetti: Array<number>,
        public idSoggettoNotifica: number,
        public selected: boolean
    ) { }
}