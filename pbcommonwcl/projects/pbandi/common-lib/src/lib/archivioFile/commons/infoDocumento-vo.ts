/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InfoDocumentoVo {
    constructor(
        public tipo: string,
        public nome: string,
        public ultimaModifica: string,
        public dimensione: string,
        public associato: boolean,
        public inviato: boolean,
        public info: string,
        public idDocumentoIndex: string,
        public idFolder: string
    ) { }
}