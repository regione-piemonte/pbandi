/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class StoricoSaldoStralcioDTO{
    public  idSaldoStralcio: number; 
	public  nome: string;
	public  cognome: string;
	public  descEsito: string; 
	public  descRecupero: string; 
	public  descSaldoStralcio: string; 
	public  dataEsito: Date; 				//  dt_esito
	public  dataProposta: Date;				//	Dt_proposta
	public  dataAcettazione: Date; 			//	Dt_accettazione
	public  impDebitore: number; 				//	imp_debitore
	public  impConfindi: number; 				//	imp_confidi
	public  dataPagamDebitore: Date;			//	Dt_pagam_debitore
	public  dataPagamConfidi: Date;			//	Dt_pagam_confidi
	public  impRecuperato: number; 			//	Imp_recuperato
	public  impPerdita: number; 				//	Imp_perdita
	public  dataInserimento: Date; 			//	Dt_inserimento
	public  dataAggiornamento: Date; 		//	Dt_aggiornamento
	public  note: string;					//	Note
	public  idEsito: number;
	public  idRecupero: number; 
	
}