/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FornitoreComboDTO {
    constructor(
        public idFornitore: number,
        public descrizione: string,
        public cfValido: boolean,
        public formaGiuridicaValida: boolean
    ) { }
}