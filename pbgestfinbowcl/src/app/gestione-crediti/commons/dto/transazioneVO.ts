/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TransazioneVO{
    public  idTransazione: number; 
	public  idProgetto: number; 
	public  impRiconciliato: number; 
	public  impTransato: number; 
	public  percTransazione: number; 
	public  impPagato: number; 
	public  idBanca: number; 
	public  note:  string; 
	public  dataInizioValidita: Date; 		
	public  dataFineValidita: Date;			
	public  dataInserimento: Date;				
	public  dataAggiornamento: Date;	
	public  idUtenteIns: number;				
	public  idUtenteAgg: number; 			
	public  descBanca: string; 
}