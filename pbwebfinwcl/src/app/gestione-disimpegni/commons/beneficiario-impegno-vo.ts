/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BeneficiarioImpegnoDTO { 
    constructor(
        public descBreveTipologia: string,
        public ragioneSociale: string,
        public codice: string,
        public descTipologia: string,
        public flagPersonaFisica: string,
        public codiceFiscale: string,
        public partitaIva: string
    ) { }
}