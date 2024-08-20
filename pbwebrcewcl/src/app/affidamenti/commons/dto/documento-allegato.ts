/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoAllegato {
    constructor(
        public id: number,
        public idParent: number,
        public nome: string,
        public protocollo: number,
        public sizeFile: number,
        public isDisassociabile: boolean,
        public linkCmd: string,
        public dataInvio: string,
        public descStatoTipoDocIndex: string,
        public descBreveTipoDocIndex: string
    ) { }
}