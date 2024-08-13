/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VerificaDichiarazioneDiSpesaRequest {
    constructor(
        public idProgetto: number,
        public idBandoLinea: number,
        public dataLimiteDocumentiRendicontabili: Date,
        public idSoggettoBeneficiario: number,
        public codiceTipoDichiarazioneDiSpesa: string			// I (intermedia), F (finale), IN (integrativa).
    ) { }
}