/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoLockCheckListDTO {
    constructor(
        public esito: boolean,
        public descBreveStato: string,
        public codiceMessaggio: string
    ) { }
}