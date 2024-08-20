/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SegnalazioneCorteContiVO{

    public  idSegnalazioneCorteConti: number;  
	public  idProgetto: number; 				
	public  dataSegnalazione:Date;  		
	public  numProtocolloSegn: string;
	public  impCredResCapitale: number;
	public  impOneriAgevolaz: number;
	public  impQuotaSegnalaz: number;
	public  impGaranzia: number;
	public  flagPianoRientro: string; 
	public  dataPianoRientro:Date; 
	public  flagSaldoStralcio: string; 
	public  dataSaldoStralcio :Date; 
	public  flagPagamIntegrale: string; 
	public  dataPagamento :Date; 
	public  flagDissegnalazione: string; 
	public  dataDissegnalazione :Date;
	public  numProtocolloDiss: string;
	public  flagDecretoArchiv: string; 
	public  dataArchiv :Date; 
	public  numProtocolloArchiv: string; 
	public  flagComunicazRegione: string;
	public  note: string; 
	public  dataInizioValidita :Date; 
	public  dataFineValidita :Date;
	public  dataInserimento :Date;
	public  dataAggiornamento :Date;			
	public  idUtenteIns: number;
	public  idUtenteAgg: number;
    				
}