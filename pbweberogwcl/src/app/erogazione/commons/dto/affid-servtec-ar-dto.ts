/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AffidServtecArDTO {
    constructor(
        public idAffidServtec: number,
        public idRichiestaErogazione: number,
        public flagAffidServtec: string,
        public fornitore: string,
        public servizioAffidato: string,
        public documento: string,
        public nomeFile: string
    ) { }
}