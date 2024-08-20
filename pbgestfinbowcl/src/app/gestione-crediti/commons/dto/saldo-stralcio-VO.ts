/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaldoStralcioVO{

    public  idSaldoStralcio: number; 			//	Id_saldo_stralcio
	public  idProgetto: number; 				//	Id_progetto
	public  idAttivitaSaldoStralcio: number;	//	id_attivita_saldo_stralcio
	public  dataProposta: Date;                 //	Dt_proposta
	public  dataAcettazione: Date; 			    //	Dt_accettazione
	public  impDebitore: number; 				//	imp_debitore
	public  impConfindi: number; 				//	imp_confidi
	public  idAttivitaEsito: number;			//	Id_attivita_esito
	public  dataEsito: Date; 				    //	dt_esito
	public  flagAgevolazione: boolean
	public  dataPagamDebitore: Date;			//	Dt_pagam_debitore
	public  dataPagamConfidi: Date;			    //	Dt_pagam_confidi
	public  idAttivitaRecupero: number;		    //	Id_attivita_recupero
	public  impRecuperato: number; 			    //	Imp_recuperato
	public  impPerdita: number; 				//	Imp_perdita
	public  impDisimpegno: number; 
	public  dataInizioValidita: Date;           //	Dt_inizio_validita
	public  dataFineValidita: Date;			    //	Dt_fine_validità
	public  idUtenteIns: number;				//	Id_utente_ins
	public  idUtenteAgg: number;				//	Id_utente_agg
	public  dataInserimento: Date; 			    //	Dt_inserimento
	public  dataAggiornamento: Date; 		    //	Dt_aggiornamento
	public  note: string;					    //	Note


}