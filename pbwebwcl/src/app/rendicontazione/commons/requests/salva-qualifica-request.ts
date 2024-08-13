/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { QualificaFormDTO } from "../dto/qualifica-form-dto";

export class SalvaQualificaRequest {
    constructor(
        public qualificaFormDTO: QualificaFormDTO,
        public idUtente: number
    ) { }
}