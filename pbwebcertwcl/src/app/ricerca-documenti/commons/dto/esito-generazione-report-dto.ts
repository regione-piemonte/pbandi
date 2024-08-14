/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoGenerazioneReportDTO {
    constructor(
        public idDocumentoIndex: number,
        public nomeDocumento: string,
        public esito: boolean,
        public messagge: string
    ) { }
}