/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoAllegatoDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public hasDocumentoDiSpesaProtocollo: boolean,
        public id: number,
        public idParent: number,
        public disassociabile: boolean,
        public documentoDiSpesaElettronico: boolean,
        public idProgetto: number,
        public nome: string,
        public protocollo: string,
        public sizeFile: number,
        public flagEntita: string,
        public associato?: boolean, //solo frontend: indica se l'allegato è stato associato su db, utilizzato in gestione fornitori
        public idIntegrazioneSpesa?: string,
        public dtInvioIntegrazione?: string,
    ) { }
}