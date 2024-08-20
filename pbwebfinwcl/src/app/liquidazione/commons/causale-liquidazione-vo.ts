/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CausaleLiquidazioneDTO { 
    constructor(
        public idCausaleErogazione: number,
        public descCausale: string,
        public descBreveCausale: string,
        public idAttoLiquidazione: number,
        public dtEmissioneAtto: Date,
        public numeroAtto: string,
        public importoLiquidatoAtto: number,
    ) { }
}