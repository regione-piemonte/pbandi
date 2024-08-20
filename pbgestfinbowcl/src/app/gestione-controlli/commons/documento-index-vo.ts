/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoIndexVO {

    public idProgetto: number;
    public idModello: number;
    public numProtocollo: string;
    public idSoggDelegato: number;
    public idDocumentoIndex: number; // Chiave primaria PBANDI_T_DOCUMENTO_INDEX
    public idSoggRapprLegale: number;
    public dtVerificaFirma: Date;
    public idStatoDocumento: number;
    public versione: number;
    public dtAggiornamentoIndex: Date;
    public idUtenteIns: number;
    public idTipoDocumentoIndex: number;
    public messageDigest: string;
    public idTarget: number;
    public repository: string;
    public flagFirmaCartacea: string;
    public nomeFile: string;
    public uuidNodo: string;
    public noteDocumentoIndex: string;
    public dtMarcaTemporale: Date;
    public idUtenteAgg: number;
    public dtInserimentoIndex: Date;
    public idEntita: number;
    public idCategAnagraficaMitt: number;
    public nomeDocumento: string;					// nome univoco del file su file system
    public nomeDocumentoFirmato: string;			// nome univoco del file firmato digitalmente su file system
    public nomeDocumentoMarcato: string;			// nome univoco del file marcato temporalmente su file system
    public idDomanda: number;
    public idSoggettoBenef: number;
}