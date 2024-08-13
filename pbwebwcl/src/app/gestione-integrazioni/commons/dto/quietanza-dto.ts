/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface QuietanzaDTO {
    idPagamento: number;
    idModalitaPagamento: number;
    descModalitaPagamento: string;
    dataPagamento: Date;
    importoPagamento: number;
}