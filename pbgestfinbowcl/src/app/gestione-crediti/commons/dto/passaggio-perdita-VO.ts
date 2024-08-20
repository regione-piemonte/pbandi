/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PassaggioPerditaVO{
    
    public	idPassaggioPerdita: number; 						
    public  idProgetto: number; 					
	public  dataPassaggioPerdita: Date; 						
	public  impPerditaComplessiva: number;						
	public  impPerditaCapitale: number;						
	public  impPerditaInterressi: number; 					
	public  impPerditaAgevolaz: number; 						
	public  impPerditaMora: number;								
	public  note: string;
	public  dataInizioValidita; 						
	public  dataFineValidita;							
	public  idUtenteIns: number;							
	public  idUtenteAgg: number;							
	public  dataInserimento: Date; 							
	public  dataAggiornamento: Date; 	
}