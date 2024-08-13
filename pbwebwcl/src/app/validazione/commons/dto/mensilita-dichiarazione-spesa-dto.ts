/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface MensilitaDichiarazioneSpesaDTO {
    anno: number;
    mese: string;
    esitoValidMesi: string;
    sabbatico: string;
    idPrg: number;
    idDichSpesa: number;
    idDichMeseRipetuto: number;
    note : string ;
}