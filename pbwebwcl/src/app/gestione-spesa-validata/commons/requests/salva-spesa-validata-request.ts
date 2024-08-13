/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { RigaValidazioneItemDTO } from "src/app/validazione/commons/dto/riga-validazione-item-dto";

export class SalvaSpesaValidataRequest {
    constructor(
        public pagamentiAssociati: Array<RigaValidazioneItemDTO>,
        public idDichiarazioneDiSpesa: number,
        public idDocumentoDiSpesa: number,
        public idProgetto: number,
    ) { }
}