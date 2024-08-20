/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { LiquidazioneDTO } from "./liquidazione-vo"

export class EsitoLeggiAttoLiquidazioneDTO { 
    constructor(
        public idAttoLiquidazione: number,
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public liquidazione: LiquidazioneDTO
    ) { }
}