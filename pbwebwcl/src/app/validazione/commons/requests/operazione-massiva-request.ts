/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class OperazioneMassivaRequest {
    constructor(
        public operazione: string,                               // "VALIDARE", "INVALIDARE", "RESPINGERE".
        public idDocumentiDiSpesaDaElaborare: Array<number>,     // Elenco degli id dei documenti di spesa da elaborare.
        public idDichiarazioneDiSpesa: number
    ) { }
}