/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CausaleLiquidazioneDTO } from "./causale-liquidazione-vo"

export class ModalitaAgevolazioneLiquidazioneDTO { 
    constructor(
        public causaliLiquidazioni: Array<CausaleLiquidazioneDTO>,
        public idModalitaAgevolazione: number,
        public descModalitaAgevolazione: string,
        public descBreveModalitaAgevolaz: string,
        public quotaImportoAgevolato: number,
        public totaleImportoLiquidato: number,
    ) { }
}