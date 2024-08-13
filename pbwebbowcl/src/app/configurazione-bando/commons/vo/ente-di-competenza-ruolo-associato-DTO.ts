/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EnteDiCompetenzaRuoloAssociatoDTO { 
    constructor(
        public progrBandoLineaEnteComp: number,
        public descrizione: string,
        public ruolo: string,
        public parolaChiave: string,
        public feedbackActa: string,
        public conservazioneCorrente: number,
        public conservazioneGenerale: number,
        public indirizzoMail: string,
        public indirizzoMailPec: string
    ) { }
}