/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DettaglioGaranziaVO{

    public idTipoAgevolazione: number; 
	public tipoAgevolazione: string; 
	public importoAmmesso: number; 
	public totaleFondo: number; 
	public totaleBanca: number; 
	public dtConcessione: Date; 
	public dtErogazioneFinanziamento: Date; 
	public importoDebitoResiduo: number; 
	public importoEscusso: number; 
	public descrizioneStato: string; 
	public revocaBancaria: string; 
	public azioniRecuperoBanca: string; 
	public saldoEStralcio: string;
    public idProgetto: number;
    public statoCredito: string
}