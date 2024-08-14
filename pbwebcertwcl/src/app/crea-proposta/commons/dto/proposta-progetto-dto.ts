/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaProgettoDTO {
    constructor(
        public idDettPropostaCertif: number,
        public codiceProgetto: string,
        public denominazioneBeneficiario: string,
        public importoCertificazioneNetto: number,
        public importoErogato: number,
        public importoPagamenti: number,
        public importoRevoche: number,
        public idPreviewDettPropCer: number,
        public flagAttivo: boolean,
        public descLinea: string,
        public descBreveLinea: string,
        public checked?: boolean
    ) { }
}