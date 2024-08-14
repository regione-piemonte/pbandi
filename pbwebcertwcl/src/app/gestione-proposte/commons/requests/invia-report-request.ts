/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { PropostaCertificazioneDTO } from "src/app/shared/commons/dto/proposta-certificazione-dto";

export class InviaReportRequest {
    constructor(
        public propostaCertificazione: PropostaCertificazioneDTO
    ) { }
}