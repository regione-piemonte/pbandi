/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { CodiceDescrizioneDTO } from "./codice-descrizione-dto";
import { ContoEconomicoItem } from "./conto-economico-item";

export class InizializzaVisualizzaContoEconomicoDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public ricercaCapofila: boolean,
        public partnersCapofila: Array<CodiceDescrizioneDTO>,
        public contoEconomico: Array<ContoEconomicoItem>
    ) { }
}