/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ImpegnoDTO } from "./impegno-vo";
import { MessaggioDTO } from "./messaggio-vo";

export class EsitoOperazioneAggiornaImpegnoDTO { 
    constructor(
        public esito: boolean,
        public msg: MessaggioDTO,
        public msimpegnog: ImpegnoDTO
    ) { }
}