/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class MessaMoraVO{

  public idMessaMora: number; 
  public idProgetto: number; 
  public idAttivitaMora: number; // tipo messa in mora
  public dataMessaMora: Date; 
  public impMessaMoraComplessiva: number; 
  public impQuotaCapitaleRevoca: number; 
  public impAgevolazRevoca: number; 
  public impCreditoResiduo: number; 
  public impInteressiMora: number; 
  public dataNotifica: Date; 
  public idAttivitaRecuperoMora: number; 
  public dataPagamento: Date; 
  public note: string; 
  public dataInizioValidita: Date; 						
  public dataFineValidita: Date;							
  public idUtenteIns: number;								
  public idUtenteAgg: number;						
  public dataInserimento: Date; 							
  public dataAggiornamento: Date;
  public numeroProtocollo: string; // Aggiunto da richiesta da FP
}

