/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IstruttoreDTO } from "./istruttore-dto";

export class EsitoOperazioneAssociaProgettiAIstruttore {
    constructor(
        public esito: boolean,
        public msg: string,
        public progettiAssociati: Array<string>,
        public progettiScartati: Array<string>
    ) { }
}