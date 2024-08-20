/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class GestioneImpegnoDTO { 
    constructor(
        public idImpegniSelizioanti: Array<number>,
        public bandoLineaSelezionati: Array<number>,
        public idProgettiSelezionati: Array<number>
    ) { }
}