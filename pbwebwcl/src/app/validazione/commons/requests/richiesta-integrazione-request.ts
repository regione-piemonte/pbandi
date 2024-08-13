/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RichiediIntegrazioneRequest {
    constructor(
        public idDichiarazioneDiSpesa: number,
        public noteIntegrazione: string
    ) { }
}