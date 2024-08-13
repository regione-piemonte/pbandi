/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroRicercaDocumentiDTO {
    constructor(
        public idSoggettoBeneficiario: number,
        public idProgetto: number,
        public idTipoDocumentoIndex: number,
        public dataDal: Date,
        public dataAl: Date,
        public docInFirma: boolean,
        public docInviati: boolean,
        public idSoggetto: number,
        public codiceRuolo: string,
        public idBando: number
    ) { }
}