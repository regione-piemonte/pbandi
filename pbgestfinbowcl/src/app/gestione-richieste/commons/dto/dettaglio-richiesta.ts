/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DettaglioRichiesta {
    constructor(
        public nag: any,
        public denominazione: any,
        public partitaIva: any,
        public modalitaRichiesta: any,
        public numeroRichiesta: any,
        public dataChiusura: any,
        public storico: any,
    ) { }
}

