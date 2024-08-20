/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ContoEconomico } from "./conto-economico";
import { ContoEconomicoItem } from "./conto-economico-item";
import { EsitoFindContoEconomicoDTO } from "./esito-find-conto-economico-dto";

export class InizializzaPropostaRimodulazioneDTO {
    constructor(
        public esitoFindContoEconomicoDTO: EsitoFindContoEconomicoDTO,
        public datiContoEconomico: ContoEconomico,
        public righeContoEconomico: Array<ContoEconomicoItem>,
        public codiceVisualizzatoProgetto: string
        /*  RIBASSO D'ASTA NON PIU' GESTITO
        public flagRibassoAsta: boolean,
        public percRibassoAstaInProposta: number,
        public importoRibassoAsta: number,
        public flagUltimoRibassoAstaInProposta: boolean,
        public flagUltimoRibassoAstaInRimodulazione: boolean*/
    ) { }
}