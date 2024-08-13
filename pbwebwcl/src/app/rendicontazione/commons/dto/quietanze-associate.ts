/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoDiPagamentoDTO } from "./documento-di-pagamento-dto";

export class QuietanzeAssociateDTO {
    constructor(
        public array: Array<DocumentoDiPagamentoDTO>,
        public ggQuietanza: number 

    ) { }
}