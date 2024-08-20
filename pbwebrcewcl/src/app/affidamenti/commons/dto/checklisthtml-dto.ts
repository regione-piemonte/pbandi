/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EsitoChecklistAffidamentoDTO } from "./esito-checklist-affidamento-dto";
import { FileDTO } from "./file-dto";

export class ChecklistHtmlDTO {
    constructor(
        public allegati: Array<FileDTO>,
        public bytesVerbale: string ,  // Array<byte>
        public codStatoTipoDocIndex: string,
        public contentHtml: string,
        public esitoDefinitivo: EsitoChecklistAffidamentoDTO,
        public esitoIntermedio: EsitoChecklistAffidamentoDTO,
        public idChecklist: number,
        public idDocumentoIndex: number,
        public idProgetto: number,
        public nomeFile: string,
        public soggettoControllore: string
    ) { }
}