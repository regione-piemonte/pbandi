/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SpesaProgettoDTO { 
    constructor(
        public tipoOperazione: string,
        public importoAmmessoContributo: number,
        public totaleSpesaAmmessa: number,
        public totaleSpesaSostenuta: number,
        public avanzamentoSpesaSostenuta: number,
        public totaleSpesaValidata: number,
        public avanzamentoSpesaValidata: number
    ) { }
}