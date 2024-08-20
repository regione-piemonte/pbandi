/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FormPropostaErogazione {
constructor(
        public bando: string,
        public agevolazione: string,
        public codiceFondoFinpis: string,
        public denominazione: string,
        public codiceProgetto: string,
        public contrPreErogazione: string
    ) { }
}
