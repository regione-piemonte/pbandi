/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AliquotaRitenutaAttoDTO } from "./aliquota-ritenuta-atto-vo";
import { EsitoRitenuteDTO } from "./esito-ritenute-vo";

export class AliquotaRienutaDTO {
    constructor(
        public aliquotaRitenutaAttoDTO: Array<AliquotaRitenutaAttoDTO>,
        public esitoRitenuteDTO: EsitoRitenuteDTO
    ) { }
}