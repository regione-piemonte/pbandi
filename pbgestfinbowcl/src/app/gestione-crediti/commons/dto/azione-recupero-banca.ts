/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AzioneRecuperoBanca{
    
    public idAzioneRecupero: number;
    public idProgetto: number;  
    public idAttivitaAzione: number;  

    public dataAzione: Date;
    public numProtocollo: number;
    public note: string;
    
    public dataInizioValidita: Date; 						// data del giorno corrente
    public  dataFineValidita: Date;							// campo di solito vuota sul db 
    public dataInserimento: Date; 							// data del giorno corrente
    public  dataAggiornamento: Date;

    public idUtenteIns: number;
    public idUtenteAgg: number; 

    

    

}