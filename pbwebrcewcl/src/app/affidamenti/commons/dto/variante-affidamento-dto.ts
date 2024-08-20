/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VarianteAffidamentoDTO {
    constructor(
        public idVariante: number,
        public importo: number,
        public idTipologiaVariante: number,
        public idAppalto: number,
        public note: string,
        public descrizioneTipologiaVariante: string,
        public dtInserimento: Date,
        public dtInvioVerificaAffidamento: Date,
        public flgInvioVerificaAffidamento: string
    ) { }
}