/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FormElencoDistinte {
constructor(
        public dataCreazioneDa: Date,
        public dataCreazioneA: Date,
        public beneficiario: string,
        public bando: string,
        public codiceFondoFinpis: string,
        public agevolazione: string,
        public progetto: string,
        public distinta: string,
    ) { }
}
