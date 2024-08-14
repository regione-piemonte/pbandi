/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoDescrizione } from "../dto/documento-descrizione";

export class ModificaAllegatiRequest {
    constructor(
        public documentiDescrizioni: Array<DocumentoDescrizione>
    ) { }
}