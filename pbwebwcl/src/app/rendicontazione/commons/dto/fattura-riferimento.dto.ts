/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FatturaRiferimentoDTO {
    constructor(
        public idDocumentoDiSpesa: number,
        public descrizione: string,
        public importoRendicontabile: number,
        public importoTotaleDocumentoIvato: number,
        public importoTotaleQuietanzato: number
    ) { }
}