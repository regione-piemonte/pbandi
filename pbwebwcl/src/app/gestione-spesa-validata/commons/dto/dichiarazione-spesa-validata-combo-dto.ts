/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface DichiarazioneSpesaValidataComboDTO {
    codice: string;
    descrizione: string;
    descBreveTipoDichiarazioneSpesa: string;
    noteRilievoContabile: string;
    dtRilievoContabile: Date;
    dtChiusuraRilievi: Date;
    dtConfermaValiditaRilievi: Date;
}