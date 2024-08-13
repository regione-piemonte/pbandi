/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface MensilitaProgettoDTO {
    anno: number;
    mese: string;
    idPrg: number;
    esito: string;
    sabbatico: string;
    idDichSpesa: number;
    erogato: boolean;
    note : string;
}