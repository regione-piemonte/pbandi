/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RelazioneTecnicaDTO{

    public idRelazioneTecnica:  number; 
    public idDocumentoIndex: number ; 
    public tipoRelazioneTecnica: number ; 
    public idProgetto: number ; 
    public nomeFile: string;
    public flagValidato : string;
    public note:  string; 
}