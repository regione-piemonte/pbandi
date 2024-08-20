/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RevocaBancariaDTO{
    
        public idRevoca: number;
        public idProgetto: number;  

        public dataRicezComunicazioneRevoca: Date;
        public dataRevoca: Date;  
        public impDebitoResiduoBanca: number; 
        public impDebitoResiduoFinpiemonte: number;
        public perCofinanziamentoFinpiemonte: number;
        public numeroProtocollo: string;
        public note: string;
        
        public dataInizioValidita: Date; 						// data del giorno corrente
        public  dataFineValidita: Date;							// campo di solito vuota sul db 
        public dataInserimento: Date; 							// data del giorno corrente
        public  dataAggiornamento: Date;

        public idUtenteIns: number;
        public idUtenteAgg: number;

        public allegati : any;

}