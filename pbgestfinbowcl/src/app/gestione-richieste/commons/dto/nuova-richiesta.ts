/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class NuovaRichiesta {
    constructor(
        public idTipoRichiesta: any,
        public idUtenteIns: any,
        public numeroDomanda: any,
        public flagUrgenza: any,
        public codiceFiscale: any,
        public codiceBando: any,
        public codiceProgetto: any
    ) { }
}


