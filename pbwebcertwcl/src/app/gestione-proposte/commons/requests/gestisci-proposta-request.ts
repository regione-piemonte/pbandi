/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FiltroRicercaProgettiNP } from "../dto/filtro-ricerca-progetti-np";

export class GestisciPropostaRequest {
    constructor(
        public filtro: FiltroRicercaProgettiNP
    ) { }
}