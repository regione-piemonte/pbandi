/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AllegatiPrecedentementeVO {

    constructor(
        public idIntegrazione: number,
        public nomeFile: string,
        public dataInserimento: string,
        public data: string,
        public dataRichiesta: string,
        public note: string,
    ) { }
}