/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FolderVo {
    constructor(
        public dtAggiornamento: Date,
        public dtInserimento: Date,
        public empty: boolean,
        public fileSize: number,
        public id: number,
        public idEntita: number,
        public idProgettoFolder: number,
        public idTarget: number,
        public joined: boolean,
        public locked: boolean,
        public parent: string,
        public text: string,
        public type: string
    ) { }
}