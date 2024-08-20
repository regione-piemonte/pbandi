/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ContoEconomicoItem } from "./conto-economico-item";
import { EsitoFindContoEconomicoDTO } from "./esito-find-conto-economico-dto";

export class InizializzaRimodulazioneIstruttoriaDTO {
    constructor(
        public esitoFindContoEconomicoDTO: EsitoFindContoEconomicoDTO,
        public righeContoEconomico: Array<ContoEconomicoItem>,
        public codiceVisualizzatoProgetto: string
    ) { }
}