/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FilesVo } from './files-vo';
export class FoldersVo {
    constructor(
        public dtAggiornamento: Date,
        public dtInserimento: Date,
        public files: Array<FilesVo>,
        public folders: Array<FoldersVo>,
        public idFolder: number,
        public idPadre: number,
        public idEntita: number,
        public idProgettoFolder: number,
        public idSoggettoBen: number,
        public nomeFolder: string
    ) { }
}