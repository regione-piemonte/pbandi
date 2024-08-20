/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProvinciaDTO { 
    constructor(
        public descrizione: string,
        public idProvincia: number,
        public idRegione: number,
        public sigla: string
    ) { }
}