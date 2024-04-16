/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AttivitaVO } from "./attivita-vo";

export class RicercaAttivitaPrecedenteDTO {
    constructor(
        public esisteRicercaPrecedente: boolean,
        public listaAttivita: Array<AttivitaVO>,
        public progrBandoLineaIntervento: number,
        public idProgetto: number,
        public descAttivita: string
    ) { }
}