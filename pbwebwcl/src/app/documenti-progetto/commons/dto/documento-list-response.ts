/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ApiMessage } from "./api-message";
import { Documento } from "./documento";

export class DocumentoListResponse {
    constructor(
        public esitoPositivo: boolean,
        public apiMessages: Array<ApiMessage>,
        public documentiList: Array<Documento>
    ) { }
}