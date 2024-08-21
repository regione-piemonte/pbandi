/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FilesVo {
    constructor(
        public bytes: number,
        public codiceVisualizzato: string,
        public descBreveStatoDocSpesa: string,
        public descStatoTipoDocIndex: string,
        public dtAggiornamento: Date,
        public dtEntita: Date,
        public dtInserimento: Date,
        public entityAssociated: string,
        public flagEntita: boolean,
        public idDocumentoIndex: number,
        public idEntita: number,
        public idFolder: number,
        public idProgetto: number,
        public idStatoDocumentoSpesa: number,
        public idTarget: number,
        public isLocked: boolean,
        public nomeFile: string,
        public numProtocollo: number,
        public sizeFile: number
    ) { }
}