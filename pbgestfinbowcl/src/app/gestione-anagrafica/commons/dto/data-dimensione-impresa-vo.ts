/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DettaglioImpresa } from "../dettaglioImpresa";

export class DatiDimensioneImpresaVO {
    constructor(
        public numeroDomanda: string,
        public descDimImpresa: string,
        public dataInseriemnto: Date,
        public dataValutazioneEsito: Date,
        public anno: number,
        public dettaglio: Array<DettaglioImpresa>
    ) { }
}