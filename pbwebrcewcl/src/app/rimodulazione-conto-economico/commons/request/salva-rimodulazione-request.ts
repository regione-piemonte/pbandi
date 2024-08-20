/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { VociDiEntrata } from "src/app/rimodulazione-conto-economico-a20/commons/conto-economico-vo";
import { AltriCostiDTO } from "../dto/altri-costi-dto";
import { ContoEconomicoItem } from "../dto/conto-economico-item";

export class SalvaPropostaRimodulazioneRequest {
    constructor(
        public idProgetto: number,
        public contoEconomicoItemList: Array<ContoEconomicoItem>,
        public altriCosti?: Array<AltriCostiDTO>,
    ) { }
}

export class SalvaRimodulazioneRequest extends SalvaPropostaRimodulazioneRequest { }