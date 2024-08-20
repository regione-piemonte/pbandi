/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FinanziamentoFonteFinanziaria } from "./finanziamento-fonte-finanziaria";
import { ModalitaAgevolazione } from "./modalita-agevolazione";

export class InizializzaConcludiRimodulazioneDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public tipoOperazione: string,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public fontiFiltrate: Array<FinanziamentoFonteFinanziaria>,
        public listaFonti: Array<FinanziamentoFonteFinanziaria>,
        public totaleSpesaAmmessaInRimodulazione: number,
        public totaleUltimaSpesaAmmessa: number,
        public importoUltimaPropostaCertificazionePerProgetto: number,
        public importoFinanziamentoBancario: number,
        public importoImpegnoGiuridico: number,
        public dataConcessione: Date
    ) { }
}