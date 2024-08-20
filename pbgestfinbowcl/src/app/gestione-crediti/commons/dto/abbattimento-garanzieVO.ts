/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AbbattimentoGaranzieVO{
    			
	public  idAbbattimGaranzie: number; 		//	Id_abbattim_garanzie
	public  idProgetto: number;				//	id_progetto
	public  idAttivitaGaranzie: number; 		//	id_attivita_garanzie
	public  dataAbbattimGaranzie: Date; 		//	dt_ abbattim_garanzie
	public  impIniziale: number; 				//	imp_iniziale
	public  impLiberato: number;				//	imp_liberato
	public  impNuovo: number;				//	imp_nuovo
	public  note: string;					//	note
	public  dataInizioValidita: Date; 		//	DT_INIZIO_VALIDITA
	public  dataFineValidita: Date;			//	DT_FINE_VALIDITA
	public  dataInserimento: Date;			//	DT_INSERIMENTO
	public  dataAggiornamento: Date;			//	DT_AGGIORNAMENTO
	public  idUtenteIns: number;				//	ID_UTENTE_INS
	public  idUtenteAgg: number;                //  ID_UTENTE_AGG

}