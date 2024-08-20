/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiDettaglioGaranziaVO{

    public idProgetto: number; 
	public idModalitaAgevolazione: number;
	public idModalitaAgevolazioneRif: number;
	public descBreveModalitaAgevolazione: string;
	public descModalitaAgevolazione: string;
	public importoAmmesso: number;
	public totaleFondo: number;
	public totaleBanca: number;
	public dataConcessione: Date;
	public dataErogazione: Date;
	public importoDebitoResiduo: number; // Da amministrativo contabile
	public isImportoDebitoLoading: boolean = false; // Usato per lo spinner
	public importoEscusso: number;
	public idEscussione: number;
	public idTipoEscussione: number;
	public idStatoEscussione: number;
	public statoCredito: string;
	public revocaBancaria: string;
	public azioniRecuperoBanca: string;
	public saldoStralcio: string;
}