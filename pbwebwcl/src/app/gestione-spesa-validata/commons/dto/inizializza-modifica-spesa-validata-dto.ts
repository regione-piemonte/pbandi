/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoAllegatoDTO } from "src/app/rendicontazione/commons/dto/documento-allegato-dto";
import { DocumentoDiSpesa } from "src/app/rendicontazione/commons/dto/documento-di-spesa";
import { DocumentoAllegatoPagamentoDTO } from "src/app/validazione/commons/dto/documento-allegato-pagamento-dto";
import { RigaNotaDiCreditoItemDTO } from "src/app/validazione/commons/dto/riga-nota-di-credito-item-dto";
import { RigaValidazioneItemDTO } from "src/app/validazione/commons/dto/riga-validazione-item-dto";

export class InizializzaModificaSpesaValidataDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public documentoDiSpesa: DocumentoDiSpesa,
        public pagamentiAssociati: Array<RigaValidazioneItemDTO>,
        public noteDiCredito: Array<RigaNotaDiCreditoItemDTO>,
        public esistePropostaCertificazione: boolean,
        public bottoneSalvaVisibile: boolean,
        public allegatiDocumento: Array<DocumentoAllegatoDTO>,
        public allegatiPagamento: Array<DocumentoAllegatoPagamentoDTO>,
        public allegatiFornitore: Array<DocumentoAllegatoDTO>,
        public allegatiNoteDiCredito: Array<DocumentoAllegatoDTO>
    ) { }
}