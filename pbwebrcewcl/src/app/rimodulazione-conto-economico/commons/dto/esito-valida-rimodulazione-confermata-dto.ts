/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ModalitaAgevolazione } from "./modalita-agevolazione";

export class EsitoValidaRimodulazioneConfermataDTO {
    constructor(
        public esito: boolean,
        public erroreBloccante: boolean,
        public messaggi: Array<string>,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>
    ) { }
}