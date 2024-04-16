/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ProgettoVO } from "../vo/progetto-vo";

export class NotificaRequest {
    constructor(
        public progrBandoLineaIntervento: number,
        public idProgetto: number,
        public elencoPrj: Array<ProgettoVO>
    ) { }
}