/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AvvisoDTO } from "./avviso-dto";
import { CodiceDescrizioneDTO } from "./codice-descrizione-dto";

export class InizializzaGestioneNewsDTO {
    constructor(
        public avvisi: Array<AvvisoDTO>,
        public tipiAnagrafica: Array<CodiceDescrizioneDTO>
    ) { }
}