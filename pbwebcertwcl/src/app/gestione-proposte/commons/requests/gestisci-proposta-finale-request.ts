/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class GestisciPropostaIntermediaFinaleRequest {
    constructor(
        public nomeBandoLinea: string,
        public codiceProgetto: string,
        public denominazioneBeneficiario: string,
        public idLineaDiIntervento: number
    ) { }
}