/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface EsitoImportoSaldoDTO {
    esito: boolean;
    messaggio: string;
    importoSpeso: number;
    sommaErogato: number;
}