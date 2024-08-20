/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FinanziamentoFonteFinanziaria } from "../dto/finanziamento-fonte-finanziaria";
import { ModalitaAgevolazione } from "../dto/modalita-agevolazione";

export class ValidaRimodulazioneConfermataRequest {
    constructor(
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public fontiFiltrate: Array<FinanziamentoFonteFinanziaria>,
        public totaleSpesaAmmessaRimodulazione: number,
        public idBando: number,
        public rimodulazioneInIstruttoria: boolean
    ) { }
}