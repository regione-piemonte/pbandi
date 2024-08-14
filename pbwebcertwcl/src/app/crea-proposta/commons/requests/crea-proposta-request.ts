/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizione } from "src/app/shared/commons/dto/codice-descrizione";
import { PropostaCertificazioneDTO } from "src/app/shared/commons/dto/proposta-certificazione-dto";

export class CreaPropostaRequest {
    constructor(
        public propostaCertificazione: PropostaCertificazioneDTO,
        public lineeDiInterventoDisponibili: Array<CodiceDescrizione>
    ) { }
}