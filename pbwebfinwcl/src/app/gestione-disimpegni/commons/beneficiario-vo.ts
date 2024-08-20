/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BeneficiarioVo { 
    constructor(
        public idImpegno: Array<number>,
        public progetti: Array<number>
    ) { }
}