/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class NewRichiediIntegrazioneRequest {
    constructor(
        public idDichiarazioneDiSpesa: number,
        public numGiorniScadenza: number,
        public idStatoRichiesta: number
    ) { }
}