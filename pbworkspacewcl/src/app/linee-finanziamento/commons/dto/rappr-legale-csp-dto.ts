/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IndirizziRapprLegaleCspDTO } from "./indirizzi-rappr-legale-csp-dto";

export class RapprLegaleCspDTO {
    constructor(
        public codiceFiscaleSoggetto: string,
        public cognome: string,
        public nome: string,
        public sesso: string,
        public dtNascita: Date,
        public indirizzi: Array<IndirizziRapprLegaleCspDTO>,
        public dtNascitaStringa: string,
        public indirizzoSelected?: IndirizziRapprLegaleCspDTO    //solo frontend
    ) { }
}