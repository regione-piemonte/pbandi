/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { FileDTO } from "./file-dto";

export class ChecklistAffidamentoHtmlDTO {
    constructor(

        public codStatoTipoDocIndex: string,
        public contentHtml: string,
        public fasiHtml: string,
        public idChecklist: number,
        public idDocumentoIndex: number,
        public idProgetto: number,
        public soggettoControllore: string,
        public bytesVerbale: string,   // Array<byte> : usato prima del multi-upload
        public allegati: Array<File> //Array<FileDTO>
        ){ }
}