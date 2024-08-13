/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RigaRicercaDocumentiDiSpesaDTO {
    constructor(
        public idDocumentoDiSpesa: number,
        public idTipoDocumentoDiSpesa: number,
        public descBreveTipoDocumentoDiSpesa: string,
        public descrizioneTipologiaDocumento: string,
        public idStatoDocumentoSpesa: number,
        public descrizioneStatoDocumentoSpesa: string,		// colonna 'stato validazione'
        public task: string,
        public tipoInvio: string,							// D = digitale; C = cartaceo		
        public numeroDocumento: string,
        public denominazioneFornitore: string,
        public importoTotaleDocumento: number,
        public importoTotaleValidato: number,
        public dataDocumento: Date,
        public checked?: boolean                            //solo frontend
    ) { }
}