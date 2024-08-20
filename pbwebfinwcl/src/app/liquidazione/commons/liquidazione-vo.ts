/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class LiquidazioneDTO { 
    constructor(
        public idAttoLiquidazione: number,
        public idCausaleLiquidazione: number,
        public percLiquidazioneEffettiva: number,
        public importoLiquidazioneEffettiva: number,
        public idModalitaAgevolazione: number,
        public codCausaleLiquidazione: string,
        public idProgetto: number,
        public idSoggetto: number,
        public idSettoreEnte: number,
        public descStatoAtto: string,
        public idStatoAtto: number,
        public numeroDocumentoSpesa: number,
    ) { }
}