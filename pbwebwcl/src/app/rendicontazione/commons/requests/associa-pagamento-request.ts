/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { PagamentoFormDTO } from "../dto/pagamento-form-dto";
import { PagamentoVoceDiSpesaDTO } from "../dto/pagamento-voce-di-spesa-dto";
import { VoceDiSpesaDTO } from "../dto/voce-di-spesa-dto";

export class AssociaPagamentoRequest {
    constructor(
        public pagamentoFormDTO: PagamentoFormDTO,
        public idDocumentoDiSpesa: number,
        public idProgetto: number,
        public idBandoLinea: number,
        public forzaOperazione: boolean,		// se true alcuni controlli vengono ignorati.
        public validazione: boolean,			    // true se si è in Validazione o in ValidazioneFinale.
        public vociDiSpesa: Array<PagamentoVoceDiSpesaDTO>
    ) { }
}