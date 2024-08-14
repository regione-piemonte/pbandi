/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroRicercaProgettiNP {
    constructor(
        public idLineaIntervento: string,
        public denominazioneBeneficiario: string,
        public codiceProgetto: string,
        public nomeBandoLinea: string,
        public progettiModificati: boolean
    ) { }
}