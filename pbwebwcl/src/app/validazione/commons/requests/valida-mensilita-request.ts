/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface ValidaMensilitaRequest {
    idPrg: number;
    mese: string;
    anno: number;
    idDichSpesa: number;
    esitoValidMesi: String;
    note : string ;
}