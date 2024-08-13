/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiFatturaElettronica {
    constructor(
        // Dati generali documento
        public numeroDocumento: string,
        public dataDocumentoDiSpesa: Date,
        public descrizioneDocumentoDiSpesa: string,
        public flagElettronico: string,				// S o N
        public flagElettXml: string,					// S o N : marca che è stato allegata una fattura elettronica.

        // Importi
        public imponibile: number,
        public importoIva: number,
        public importoTotaleDocumentoIvato: number,

        // Fornitore
        public denominazioneFornitore: string,
        public codiceFiscaleFornitore: string,
        public partitaIvaFornitore: string,
        public flagPubblicoPrivatoFornitore: string,  	// S o N
        public idTipoFornitore: number,
        public codTipologiaFornitore: string,			// EG o PF

        // Appalto
        public idAppalto: number,
        public descrizioneAppalto: string
    ) { }
}