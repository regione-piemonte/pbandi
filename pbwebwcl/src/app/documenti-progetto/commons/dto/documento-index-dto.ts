/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoIndexDTO {
    constructor(
        public allegati: string,
        public beneficiario: string,
        public codiceProgettoVisualizzato: string,
        public codStatoDoc: string,
        public codTipoDoc: string,
        public descErrore: string,
        public descTipoDocumento: string,
        public dtCreazione: string,
        public dtSign: string,
        public dtTimestamp: string,
        public documento: string,
        public flagFirmaCartacea: boolean,
        public flagFirmaAutografa: boolean,
        public flagTrasmDest: boolean,
        public idDocumentoIndex: string,
        public idProgetto: string,
        public idBandoLinea: string,
        public isBR50: string,
        public note: string,
        public protocollo: string,
        public signed: boolean,
        public signable: boolean,
        public signValid: boolean,
        public timeStamped: boolean,
        public tipoInvioDs: string,
        public idCategAnagraficaMitt: string,
        public descCategAnagraficaMitt: string,
        public idEntita: string,
        public idTarget: string,
        public iconDownloadDS: string
    ) { }
}