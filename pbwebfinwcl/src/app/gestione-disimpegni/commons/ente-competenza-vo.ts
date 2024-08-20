/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EnteCompetenzaDTO { 
    constructor(
        public idEnteCompetenza: number,
        public descEnte: string,
        public descBreveEnte: string,
        public idTipoEnte: number,
        public descTipoEnte: string,
        public descBreveTipoEnte: string
    ) { }
}