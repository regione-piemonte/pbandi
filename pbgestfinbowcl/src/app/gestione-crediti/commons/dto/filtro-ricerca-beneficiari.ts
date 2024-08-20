/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroRicercaBeneficiari {
    constructor(
        public codiceFiscale: string,
        public nag: string,
        public partitaIva: string,
        public denominazione: string,
        public descBando: string,
        public codiceProgetto: string,

        //public expandedState: boolean
    ) { }
}