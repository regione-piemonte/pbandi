/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class DatiIntegrazioneDsDTO {
    constructor(
        public idIntegrazioneSpesa: number,
        public dataRichiesta: string,
        public dataInvio: string,
        public descrizione: string,
        public allegati: Array<DocumentoAllegatoDTO>
    ) { }
}