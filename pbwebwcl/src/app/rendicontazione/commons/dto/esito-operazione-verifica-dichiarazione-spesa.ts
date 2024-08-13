/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoDiSpesa } from "./documento-di-spesa";

export class EsitoOperazioneVerificaDichiarazioneSpesa {
    constructor(
        public esito: boolean,
        public documentiDiSpesa: Array<DocumentoDiSpesa>,
        public messaggi: Array<string>
    ) { }
}