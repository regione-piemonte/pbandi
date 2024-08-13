/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface DocumentoDiSpesaSospesoDTO {
    idDocumentoDiSpesa: number;
    fornitore: string;
    documento: string;
    importo: number;
    note: string;
    data: Date;
}