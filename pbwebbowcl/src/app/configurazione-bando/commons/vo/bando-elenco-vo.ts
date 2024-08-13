/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoElencoVo {
    constructor(
        public bando: string,
        public bandoLinea: BandoLineaVo[]
    ) { }
}

export class BandoLineaVo {
    constructor(
        public bando: string
    ) { }
}