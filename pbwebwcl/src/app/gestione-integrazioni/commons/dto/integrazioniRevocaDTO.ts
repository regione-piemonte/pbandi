/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class IntegrazioniRevocaDTO {

    constructor(
        public nRevoca: number,
        public dataRichiesta: string,
        public dataNotifica: string,
        public dataScadenza: string,
        public dataInvio: string,
        public idStatoRichiesta: string,
        public statoRichiesta: string,
        public longStatoRichiesta: string,
        public invia: string
    ) { }
}