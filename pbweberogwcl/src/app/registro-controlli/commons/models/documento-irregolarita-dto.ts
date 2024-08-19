/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface DocumentoIrregolaritaDTO {
  idDocumentoIndex: number;
  dtInserimentoIndex?: Date;
  nomeFile: string;
  noteDocumentoIndex?: string;
  bytesDocumento?: any
}