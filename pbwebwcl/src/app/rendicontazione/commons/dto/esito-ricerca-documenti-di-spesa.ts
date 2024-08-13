/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ElencoDocumentiSpesaItem } from "./elenco-documenti-spesa-item";

export class EsitoRicercaDocumentiDiSpesa {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public documenti: Array<ElencoDocumentiSpesaItem>
    ) { }
}