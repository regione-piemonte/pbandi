/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ContoEconomicoItem } from "./conto-economico-item";

export class EsitoSalvaPropostaRimodulazioneDTO {
    constructor(
        public esito: boolean,
        public messaggi: Array<string>,
        public righeContoEconomicoAggiornato: Array<ContoEconomicoItem>
    ) { }
}

export class EsitoSalvaRimodulazioneDTO extends EsitoSalvaPropostaRimodulazioneDTO { }