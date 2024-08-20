/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CoordinateBancarieDTO { 
    constructor(
        public bic: string,
        public swift: string,
        public cin: string,
        public abi: string,
        public cab: string,
        public filiale: string,
        public banca: string,
        public iban: string
    ) { }
}