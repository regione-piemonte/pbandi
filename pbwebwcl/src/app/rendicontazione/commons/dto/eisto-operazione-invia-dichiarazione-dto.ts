/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoOperazioneInviaDichiarazioneDTO {
    constructor(
        public esito: boolean,
        public msg: string,
        public nomeFileDichiarazioneSpesa: string,
        public idDichiarazioneSpesa: number,
        public idDocumentoIndex: number
    ) { }
}