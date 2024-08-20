/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FinanziamentoFonteFinanziaria } from "../dto/finanziamento-fonte-finanziaria";
import { ModalitaAgevolazione } from "../dto/modalita-agevolazione";

export class SalvaRimodulazioneConfermataRequest {
    constructor(
        public idProgetto: number,
        public idContoEconomico: number,
        public idSoggettoBeneficiario: number,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public listaFonti: Array<FinanziamentoFonteFinanziaria>,
        public importoImpegnoGiuridico: number,
        public importoFinanziamentoBancario: number,
        public dataConcessione: Date,
        public riferimento: string,
        public note: string
    ) { }
}