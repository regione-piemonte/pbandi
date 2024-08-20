/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipoFondoDTO { 
    constructor(
        public idTipoFondo: number,
        public descBreveTipoFondo: string,
        public descTipoFondo: string
    ) { }
}