/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FonteFinanziariaDTO } from "./fonte-finanziaria-vo"
import { RipartizioneImpegniLiquidazioneDTO } from "./ripartizione-impegni-liquidazione-vo"

export class EsitoLeggiRiepilogoFondiDTO { 
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public fontiFinanziarie: Array<FonteFinanziariaDTO>,
        public ripartizioneImpegniLiquidazione: Array<RipartizioneImpegniLiquidazioneDTO>,
        public massimoNumeroImpegni: number,
        public massimoNumeroImpegniAllineabiliBil: number,
    ) { }
}