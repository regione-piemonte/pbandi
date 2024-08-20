/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InfoDettaglioRicercaGaranzieVO{
  
        // REVOCA BANCARIA //
	public revoca_idRevocaBancaria: number;
	public revoca_dataRicezioneComunicazione: Date;
	public revoca_dataRevoca: Date;
	public revoca_importoDebitoResiduoBanca: number;
	public revoca_importoDebitoResiduoFinpiemonte: number;
	public revoca_numeroProtocollo: string;
	public revoca_note: string;	
	
	// AZIONE RECUPERO BANCA //
	public azione_idAzioneRecuperoBanca: number;
	public azione_idAttivitaAzione: number;
	public azione_descAttivitaAzione: string;
	public azione_dataAzione: Date;
	public azione_numeroProtocollo: string;
	public azione_note: string;
	
	// SALDO E STRALCIO //
	public saldo_idSaldoStralcio: number;
	public saldo_idAttivita: number;
	public saldo_descBreveAttivita: string;
	public saldo_descAttivita: string;
	public saldo_dataProposta: Date;
	public saldo_dataAccettazione: Date;
	public saldo_importoDebitore: number;
	public saldo_importoConfidi: number;
	public saldo_importoDisimpegno: number;
	public saldo_flagAgevolazione: boolean;
	public saldo_idAttivitaEsito: number;
	public saldo_descBreveEsito: string;
	public saldo_descEsito: string;
	public saldo_dataEsito: Date;
	public saldo_dataPagamentoDebitore: Date;
	public saldo_dataPagamentoConfidi: Date;
	public saldo_idAttivitaRecupero: number;
	public saldo_descBreveRecupero: string;
	public saldo_descRecupero: string;
	public saldo_importoRecuperato: number;
	public saldo_importoPerdita: number;
	public saldo_note: string;
	
	// COMMON //
	public com_dataInizioValidita: Date;
	public com_dataInserimento: Date;
	public com_dataAggiornamento: Date;
	public com_utenteIns: string;
	public com_utenteAgg: string;
}