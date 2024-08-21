/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SalvaFileVo {
    constructor(
        public nomeFile: string,
        public esito: boolean,
        public messaggio: string,
        public idDocumentoIndex: number
    ) { }
}