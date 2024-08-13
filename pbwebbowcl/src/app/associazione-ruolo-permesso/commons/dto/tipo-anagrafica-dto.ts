/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipoAnagraficaDTO {
    constructor(
        public descBreveTipoAnagrafica: string,
        public descTipoAnagrafica: string
    ) { }
}

export class TipoAnagraficaDTOCheck extends TipoAnagraficaDTO {
    public checked: boolean;
}