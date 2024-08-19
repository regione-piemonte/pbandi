/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Soppressione } from "../requests/soppressione";

export class ResponseInizializzaSoppressioniDTO {
    constructor(
        public soppressioni: Array<Soppressione>,
        public importoCertificabileLordo: number,
        public importoTotaleDisimpegni: number,
        public insModDelAbilitati: boolean
    ) { }
}