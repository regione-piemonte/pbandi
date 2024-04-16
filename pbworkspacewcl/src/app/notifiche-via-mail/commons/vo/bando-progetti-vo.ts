/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ProgettoNotificheVO } from "./progetto-notifiche-vo";

export class BandoProgettiVO {
    constructor(
        public nomeBandoLinea: string,
        public progrBandoLineaIntervento: number,
        public progetti: Array<ProgettoNotificheVO>,
        public isExpanded?: boolean,
        public allSelected?:boolean
    ) { }
}