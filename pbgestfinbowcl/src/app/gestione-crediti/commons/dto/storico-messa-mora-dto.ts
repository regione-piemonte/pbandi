/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StoricoMessaMoraDTO{

    public  idMessaMora: number; 
	public  nome: string;
	public  cognome: string;
	public  descMessaMora: string; // tipo messa in mora
	public  descRecupero: string;
	public  dataMessaMora: Date; 
	public  impMessaMoraComplessiva: number; 
	public  impQuotaCapitaleRevoca: number; 
	public  impAgevolazRevoca: number; 
	public  impCreditoResiduo: number; 
	public  impInteressiMora: number; 
	public  dataNotifica: Date;
	public  dataPagamento: Date; 
	public  dataInserimento: Date; 
	public  note: string;
}