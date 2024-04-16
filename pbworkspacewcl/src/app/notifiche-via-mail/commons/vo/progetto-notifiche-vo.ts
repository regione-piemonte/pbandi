/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoNotificheVO {
    constructor(
        public idProgetto: number,
        public codiceVisualizzato: string,
        public cup: string,
        public titoloProgetto: string,
        public isAssociated: boolean
    ) { }
}