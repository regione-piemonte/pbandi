/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ReportCampionamentoDTO {
    constructor(
        public idDocumentoIndex: number,
        public idNormativa: number,
        public descNormativa: string,
        public idAnnoContabile: number,
        public descAnnoContabile: string,
        public dataCampionamento: Date,
        public nomeFile: string
    ) { }
}