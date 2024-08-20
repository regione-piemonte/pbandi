/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { sanitizeIdentifier } from "@angular/compiler";

export class DettaglioImpresa {
    constructor(
        public  annoRiferimento: string,
        public  unitaLavorativeAnnue: string,
        public  fatturato: string,
        public  totaleBilancioAnnuale: string,
    ) {}
}
