/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StoricoAzioneRecuperoBancaDTO{

    public nome: string;
    public cognome: string;
    public dataInserimento: Date;
    public dataAzione: Date;
    public dataAggiornamento: Date;
    public descrizioneAttivita: string;
    public note: string
    public numProtocollo: number; 
    public idAzioneRecuperoBanca: number;
 
}