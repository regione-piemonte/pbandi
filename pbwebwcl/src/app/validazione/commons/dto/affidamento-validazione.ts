/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AffidamentoValidazione {
    constructor(
        public idAppalto: number,
        public oggetto: string,
        public codProcAgg: string,
        public cigProcAgg: string,
        public idStatoAffidamento: number,
        public descStatoAffidamento: string,
        public esitoIntermedio: string,
        public flagRettificaIntermedio: string,
        public esitoDefinitivo: string,
        public flagRettificaDefinitivo: string,
        public nomeFile: string,
        public idDocIndex: number
    ) { }
}