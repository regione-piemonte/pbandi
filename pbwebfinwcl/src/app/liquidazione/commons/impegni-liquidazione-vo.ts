/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { LiquidazioneDTO } from "./liquidazione-vo";
import { RipartizioneImpegniLiquidazioneDTO } from "./ripartizione-impegni-liquidazione-vo";

export class ImpegniLiquidazioneDTO { 
    constructor(
        public liquidazione: LiquidazioneDTO,
        public ripartizioneImpegniLiquidazioneDTO: Array<RipartizioneImpegniLiquidazioneDTO>
    ) { }
}