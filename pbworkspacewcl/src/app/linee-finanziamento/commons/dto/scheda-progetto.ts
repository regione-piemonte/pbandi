/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InformazioniBase } from "./informazioni-base";
import { SedeIntervento } from "./sede-intervento";
import { SoggettoGenerico } from "./soggetto-generico";

export class SchedaProgetto {
    constructor(
        public idProgetto: string,
        public idBandoLinea: string,
        public idLineaNormativa: string,
        public idLineaAsse: string,
        public flagSalvaIntermediario: string,
        public informazioniBase: InformazioniBase,
        public sediIntervento: Array<SedeIntervento>,
        public beneficiario: SoggettoGenerico,
        public rappresentanteLegale: SoggettoGenerico,
        public intermediario: SoggettoGenerico,
        public altriSoggetti: Array<SoggettoGenerico>,
        public sedeInterventoDefault: SedeIntervento,
        public altroSoggettoDefault: SoggettoGenerico,
    ) { }
}