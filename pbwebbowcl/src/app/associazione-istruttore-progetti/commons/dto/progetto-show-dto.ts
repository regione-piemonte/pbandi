/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IstruttoreDTO } from "./istruttore-dto";

export class ProgettoShowDTO {
    constructor(
        public idProgetto: number,
        public idSoggettoBeneficiario: number,
        public idBando: number,
        public numeroIstruttoriAssociati: number,
        public titoloBando: string,
        public codiceVisualizzato: string,
        public beneficiario: string,
        public istruttoriSempliciAssociati: Array<IstruttoreDTO>,
        public progrSoggettoProgetto: number,
        public cup: string,
        public showIstrAsso: boolean
    ) { }
}