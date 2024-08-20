/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizione } from "src/app/appalti/commons/dto/codice-descrizione"
import { ModalitaAgevolazione } from "./modalita-agevolazione";
import { ProceduraAggiudicazione } from "./procedura-aggiudicazione";

export class InizializzaConcludiPropostaRimodulazioneDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public allegatiAmmessi: boolean,		                                	// nel js messo in hInvioDigitale.
        public rappresentantiLegali: Array<CodiceDescrizione>,
        public delegati: Array<CodiceDescrizione>,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public listaProcedureAggiudicazione: Array<ProceduraAggiudicazione>,
        public totaleRichiestoNuovaProposta: number
    ) { }
}