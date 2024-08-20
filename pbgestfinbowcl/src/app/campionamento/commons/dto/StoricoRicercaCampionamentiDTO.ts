/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StoricoRicercaCampionamentiDTO{

    public  idCampione :number; 
	public  descrizione:string; 
	public  descTipologiaCamp:string; 
	public  numProgettiSelezionati:number; 
	public  quotaCertEstratta:number;  
	public  idTipoCamp:number; 
	public  idFaseCamp:number; 
	public  dataCampionamento: Date; 
	public  impValidato:number; 
	public  percEstratta:number; 
	public  impValPercEstratta:number; 
	public  dataInizioValidita: Date; 		
	public  dataFineValidita: Date;			
	public  dataInserimento: Date;				
	public  dataAggiornamento: Date;	
	public  idUtenteIns:number;				
	public  idUtenteAgg:number;
	public  flagAnnullato:string;
	public  nome:string;
	public  cognome:string;
}