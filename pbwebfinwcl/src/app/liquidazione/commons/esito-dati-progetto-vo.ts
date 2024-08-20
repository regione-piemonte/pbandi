/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FideiussioneDTO } from "./fideussione-vo"
import { RichiestaErogazioneDTO } from "./richiesta-erogazione-vo"
import { SpesaProgettoDTO } from "./spesa-progetto-vo"

export class EsitoDatiProgettoDTO { 
    constructor(
        public idProgetto: number,
        public spesaProgetto: SpesaProgettoDTO,
        public fideiussioni: Array<FideiussioneDTO>,
        public richiesteErogazione: Array<RichiestaErogazioneDTO>,
        public importoTotaleRichiesto: number,
        public esito: boolean,
        public message: string,
        public params: Array<string>
    ) { }
}