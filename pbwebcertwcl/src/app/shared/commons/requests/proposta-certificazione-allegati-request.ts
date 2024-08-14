/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaCertificazioneAllegatiRequest {
    constructor(
        public idPropostaCertificazione: number,
        public codiciTipoDocumento: string[]
    ) { }
}