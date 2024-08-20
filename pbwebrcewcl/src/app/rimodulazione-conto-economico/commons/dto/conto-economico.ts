/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContoEconomico {
    constructor(
        public hasCopiaPresente: boolean,
        public dataUltimaProposta: string,
        public dataUltimaRimodulazione: string,
        public dataPresentazioneDomanda: string,
        public dataFineIstruttoria: string,
        public nascondiColonnaImportoRichiesto: boolean,
        public nascondiColonnaSpesaAmmessa: boolean
    ) { }
}