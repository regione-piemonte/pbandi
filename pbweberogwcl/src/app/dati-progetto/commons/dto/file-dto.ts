/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface FileDTO {
  bytes?: Array<ArrayBuffer>;
  codiceVisualizzato?: string;
  descBreveStatoDocSpesa?: string;
  descStatoTipoDocIndex?: string;
  dtAggiornamento?: string;
  dtEntita?: string;
  dtInserimento?: string;
  entityAssociated?: number;
  flagEntita?: string;
  idDocumentoIndex?: number;
  idEntita?: number;
  idFolder?: number;
  idProgetto?: number;
  idStatoDocumentoSpesa?: number;
  idTarget?: number;
  isLocked?: boolean;
  nomeFile?: string;
  numProtocollo?: string;
  sizeFile?: number;
}
