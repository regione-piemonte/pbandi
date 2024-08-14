/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoCertificazioneDTO {
    constructor(
        public descTipoDocIndex: string,
        public idDocumentoIndex: number,
        public nomeFile: string,
        public nomeDocumento: string,
        public descBreveTipoDocIndex: string,
        public noteDocumentoIndex: string,
        public dtInserimentoIndex: Date,
        public codiceProgettoVisualizzato: string,
        public idProgetto: number,
        public idPropostaCertificaz: number,
        public descTipoDocIndexStato: string,
        public checked?: boolean
    ) { }
}