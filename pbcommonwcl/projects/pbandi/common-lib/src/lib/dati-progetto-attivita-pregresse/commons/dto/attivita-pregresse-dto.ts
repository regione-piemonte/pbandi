/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { RigoAttivitaPregresseDTO } from "./rigo-attivita-pregresse-dto";

export class AttivitaPregresseDTO {
    constructor(
        public descAttivita: string,
        public righe: Array<RigoAttivitaPregresseDTO>,
        public data: Date,
        public idDocumentoIndex: string,
        public nomeDocumento: string,
        public idDocumentoIndexReport: string,
        public nomeDocumentoReport: string,
        public codAttivita: string
    ) { }
}