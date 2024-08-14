/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ProgettoNuovaCertificazione } from "../dto/progetto-nuova-certificazione";

export class AggiornaImportoNettoRequest {
    constructor(
        public progetti: Array<ProgettoNuovaCertificazione>
    ) { }
}