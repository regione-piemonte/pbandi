/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BeneficiarioProgettoAssociatoDTO {
    constructor(
        public idBeneficiario: number,
        public codiceFiscaleBeneficiario: string,
        public denominazioneBeneficiario: string,
        public idProgetto: number,
        public codiceProgettoVisualizzato: string,
        public titoloProgetto: string,
        public isRappresentanteLegale: boolean
    ) { }
}