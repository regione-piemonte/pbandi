/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class LiberazioneGaranteDTO {

       public operation: string;

       public idProgetto: string;
       public idUtente: string;
       
       public idLibGar: number;
       public garanteLiberato: string;
       public dataLiberazione: Date;
       public utenteModifica: string;
       public importoLiberazione: number;
       public note: string;

       public story_dataIns: any
       public story_dataAgg: any;
		
       public fineValidita: string;
}