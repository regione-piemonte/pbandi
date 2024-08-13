/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSalvaDatiBando { 
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public idBando: number
    ) { }
}