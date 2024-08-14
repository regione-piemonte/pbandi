/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroRicercaDocumentoDTO {
    constructor(
        public idLineaIntervento: number,
        public idBeneficiario: number,
        public idProposta: number,
        public idProgetto: number,
        public isDichiarazioneFinale: boolean,
        public isRicercaProposta: boolean,
        public isChecklist: boolean,
        public isRicercaPropostaProgetto: boolean,
        public isPropostaDiCertificazione: boolean,
        public statiProposte: Array<string>
    ) { }
}