/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AllegatoIntegrazioneDs {
    constructor(
        public idDocumentoIndex: string,
        public nomeFile: string,
        public flagEntita: string,
        public checked?: boolean    //solo frontend
    ) { }
}