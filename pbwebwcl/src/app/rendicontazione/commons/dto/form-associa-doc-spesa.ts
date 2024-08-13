/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FormAssociaDocSpesa {
    constructor(
        public idDocumentoDiSpesa: number,
        public task: string,
        public importoRendicontabile: number,
        public massimoRendicontabile: number,
        public isDocumentoTotalmenteRendicontato: boolean,
        public descBreveTipoDocumento: string
    ) { }
}