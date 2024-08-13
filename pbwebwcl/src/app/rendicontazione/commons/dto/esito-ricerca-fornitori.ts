/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Fornitore } from "./fornitore";

export class EsitoRicercaFornitori {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public fornitori: Array<Fornitore>
    ) { }
}