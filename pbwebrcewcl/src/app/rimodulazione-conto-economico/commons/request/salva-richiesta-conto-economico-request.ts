/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ModalitaAgevolazione } from "../dto/modalita-agevolazione";

export class SalvaRichiestaContoEconomicoRequest {
    constructor(
        public idProgetto: number,
        public idContoEconomico: number,
        public idSoggettoBeneficiario: number,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public note: string,
        public idRapprensentanteLegale: number,
        public importoFinanziamentoRichiesto: number
    ) { }
}