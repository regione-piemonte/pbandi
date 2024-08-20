/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoInviaPropostaRimodulazioneDTO {
    constructor(
        public esito: boolean,
        public idDocumentoIndex: number,
        public idContoEconomico: number,
        public nomeFile: string,
        public dataProposta: Date
    ) { }
}