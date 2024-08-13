/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StatoVo {
    constructor(
        public id: number,
        public descrizione: string
    ) { }
}

export class StatoFornitoreVo extends StatoVo { }