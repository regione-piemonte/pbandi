/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BandoVo { 
    constructor(
        public descMateria: string,
        public descModalitaAttivazione: string,
        public idBando: number,
        public idBandoLinea: number,
        public idLineaDiIntervento: number,
        public nomeBandoLinea: string,
        public titoloBando: string
    ) { }
}