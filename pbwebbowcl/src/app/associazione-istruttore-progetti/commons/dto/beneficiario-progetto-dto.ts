/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IstruttoreDTO } from "./istruttore-dto";

export class BeneficiarioProgettoDTO {
    constructor(
        public idSoggettoBeneficiario: number,
        public beneficiario: string
    ) { }
}