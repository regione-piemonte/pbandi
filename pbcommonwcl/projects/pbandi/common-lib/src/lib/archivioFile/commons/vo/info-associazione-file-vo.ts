/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AppaltoInfoVo } from "./appalto-info-vo";
import { ComFineProgInfoVo } from "./com-fine-prog-info-vo";
import { ContoEcoInfoVo } from "./conto-eco-info-vo";
import { DichiarazioneInfoVo } from "./dichiarazione-info-vo";
import { DocSpesaInfoVo } from "./doc-spesa-info-vo";
import { FornitoreInfoVo } from "./fornitore-info-vo";
import { IntegrazioneInfoVo } from "./integrazione-info-vo";
import { PagamentoInfoVo } from "./pagamento-info-vo";
import { RichiestaErogazioneInfoVo } from "./richiesta-erogazione-info-vo";

export class InfoAssociazioneFileVo {
    constructor(
        public contoEcoInfo: Array<ContoEcoInfoVo>,
        public appaltoInfo: Array<AppaltoInfoVo>,
        public comFineProgInfo: Array<ComFineProgInfoVo>,
        public docDiSpesaInfo: Array<DocSpesaInfoVo>,
        public fornitoreInfo: Array<FornitoreInfoVo>,
        public codiceProgetto: string,
        public descBandoLinea: string,
        public dichiarazioneInfo: Array<DichiarazioneInfoVo>,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idSoggettoBen: number,
        public idTarget: number,
        public integrazioneDsInfo: Array<IntegrazioneInfoVo>,
        public nomeFile: string,
        public pagamentoInfo: Array<PagamentoInfoVo>,
        public richiestaErogazioneInfo: Array<RichiestaErogazioneInfoVo>,
        public sizeFile: number,
        public titoloProgetto: string
    ) { }
}