/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RiepilogoAttoDiLiquidazionePerProgetto { 
    constructor(
        public idAttoDiLiquidazione: number,
        public modalitaAgevolazione: string,
        public causaleErogazione: string,
        public estremiAtto: string,
        public ultimoImportoAgevolato: number,
        public importoLiquidato: number
    ) { }
}