/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */

export interface AllegatoDTO {
    nomeFile: string;
    idDocumentoIndex: number;
    idTipoDocumentoIndex: number;
    idEntita: number;
    idFolder: number; 
    idTarget: number;
    isLocked: boolean;
    sizeFile: number;
}
