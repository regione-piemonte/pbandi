/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ImpegnoDTO } from "./impegno-vo";

export class BandolineaImpegnoDTO { 
    constructor(
        public idSoggetto: number,
        public progrBandolineaIntervento: number,
        public nomeBandolinea: string,
        public dotazioneFinanziaria: number,
        public impTotImpegniBandolinea: number,
        public numTotImpegniBandolinea: number,
        public impegni: Array<ImpegnoDTO>
    ) { }
}