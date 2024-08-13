/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface ParametroCompensiDTO {
    idParametroCompenso: number;
    categoria: number;
    oreSettimanali: number;
    compensoDovutoMensile: number;
    giorniLavorabiliSettimanali: number;
    orarioMedioGiornaliero: number;
    budgetInizialeTirocinante: number;
    budgetInizialeImpresa: number;
}