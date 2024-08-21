/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AppaltoInfoVo {
    constructor(
        public codProcAgg: string,
        public codiceProgettoVisualizzato: string,
        public idAppalto: number,
        public nomeBandoLinea: string,
        public oggetto: string,
    ) { }
}