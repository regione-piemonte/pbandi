/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AttivitaVO } from './attivita-vo';

export class ProgettoVO {
    constructor(
        public id: number,
        public acronimo: string,
        public codiceVisualizzato: string,
        public titolo: string,
        public attivita: Array<AttivitaVO>
    ) { }
}