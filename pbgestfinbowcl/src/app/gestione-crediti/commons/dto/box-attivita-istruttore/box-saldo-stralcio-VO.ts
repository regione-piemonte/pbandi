/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BoxSaldoStralcioVO{

	public idSaldoStralcio: number;
	public idProgetto: number;
	public idModalitaAgevolazione: number;

    public idAttivitaSaldoStralcio: number;
	public descBreveAttivitaSaldoStralcio: string;
	public descAttivitaSaldoStralcio: string;
	public dataProposta: Date;
	public dataAccettazione: Date;
	public importoDebitore: number;
	public importoConfidi: number;
	public idAttivitaEsito: number;
	public descBreveAttivitaEsito: string;
	public descAttivitaEsito: string;
	public dataEsito: Date;
	public dataPagamentoDebitore: Date;
	public dataPagamentoConfidi: Date;
	public idAttivitaRecupero: number;
	public descBreveAttivitaRecupero: string;
	public descAttivitaRecupero: string;
	public importoRecuperato: number;
	public importoPerdita: number;
	public note: string;
	public flagAgevolazione: boolean;
	public importoDisimpegno: number;

	public dataInizioValidita: Date;
	public idUtenteIns: number;
	public nomeUtenteIns: string;
	public dataAggiornamento: Date;
	public idUtenteAgg: number;
	public nomeUtenteAgg: string;

}