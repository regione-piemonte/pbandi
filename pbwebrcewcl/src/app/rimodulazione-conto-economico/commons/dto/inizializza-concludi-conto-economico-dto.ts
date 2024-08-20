/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizione } from "src/app/appalti/commons/dto/codice-descrizione";
import { ModalitaAgevolazione } from "./modalita-agevolazione";

export class InizializzaConcludiRichiestaContoEconomicoDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public rappresentantiLegali: Array<CodiceDescrizione>,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public importModificabili: boolean,
        public importoFinanziamentoRichiesto: number,
        public idContoEconomico: number,
        public totaleRichiestoInDomanda: number
    ) { }
}