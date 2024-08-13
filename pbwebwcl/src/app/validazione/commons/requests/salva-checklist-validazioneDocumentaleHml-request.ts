/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class SalvaCheckListValidazioneDocumentaleHtmlRequest{
    constructor(
        public idProgetto: number,
        public idDichiarazioneDiSpesa: number,
        public idDocumentoIndex: number,
        public stato: string,
        public checklistHtml: string,

        public idBandoLinea: number,
        public codiceProgetto: string,
        public dsIntegrativaConsentita: boolean
    ){};
}
