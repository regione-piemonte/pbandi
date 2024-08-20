/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { GestioneAllegatiVO } from "./gestione-allegati-VO";

export class NoteGeneraliVO {
    public idAnnotazione: number; 
	public idProgetto: number; 
	public note: string; 
	public dataInizioValidita: Date; 		
	public dataFineValidita: Date;			
	public dataInserimento: Date;				
	public dataAggiornamento: Date;	
	public idUtenteIns: number;				
	public idUtenteAgg: number;
	public nomeUtente: string;
	public cognomeUtente: string;

	public nomeUtenteAgg: string;
	public cognomeUtenteAgg: string;

	public allegatiPresenti: Array<GestioneAllegatiVO>;
	public nuoviAllegati: Array<GestioneAllegatiVO>;
}