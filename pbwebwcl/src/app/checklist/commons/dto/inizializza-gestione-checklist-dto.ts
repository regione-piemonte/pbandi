/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InizializzaGestioneChecklistDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public modificaChecklistAmmessa: boolean,
        public eliminazioneChecklistAmmessa: boolean
    ) { }
}