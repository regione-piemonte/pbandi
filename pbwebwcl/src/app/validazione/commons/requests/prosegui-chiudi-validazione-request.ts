/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProseguiChiudiValidazioneRequest {
    constructor(
        public noteChiusura: string,
        public dsIntegrativaConsentita: boolean,
        public idAppaltiSelezionati: Array<number>,
        public idProgetto: number,
        public idDichiarazioneDiSpesa: number
    ) { }
}