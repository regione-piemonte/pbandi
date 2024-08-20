/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ImpegnoDTO } from "./impegno-vo";

export class ProgettoImpegnoDTO { 
    constructor(
        public idProgetto: number,
        public codiceVisualizzato: string,
        public titoloProgetto: string,
        public quotaImportoAgevolato: number,
        public idSoggetto: number,
        public impTotImpegniProgetto: number,
        public numTotImpegniProgetto: number,
        public impegni: Array<ImpegnoDTO>,
        public denominazioneBeneficiario: string,
        public nomeBandoLinea: string
    ) { }
}