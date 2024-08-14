/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoNuovaCertificazione {
    constructor(
        public nuovoImportoCertificazioneNetto: number,
        public idProgetto: number,
        public idDettPropostaCertif: number,
        public idPropostaCertificazione: number,
        public nota: string
    ) { }
}