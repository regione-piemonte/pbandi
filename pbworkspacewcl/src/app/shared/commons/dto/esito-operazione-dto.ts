/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface EsitoOperazioneDTO {
    esito: boolean;
    messaggi: Array<string>;
    id: number;
}