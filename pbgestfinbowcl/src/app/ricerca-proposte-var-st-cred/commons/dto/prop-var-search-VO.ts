/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CercaPropostaVarazioneStatoCreditoSearchVO{
	/* public  idStatoCredito:number ;
	public  descStatoCredito: String;  */


	public  codiceFiscale: String; 
	public  partitaIVA: String; 
	public  denominazione: string; 
	public  codiceProgetto: string; 
	public  idAgevolazione:number ; 
	public  idBando: number; 
	public  idStatoProposta: number; 
	public  idSoggettoCF: number; 
	public  idSoggettoDenom: number; 
	public  idSoggProg: number;
	public  titoloBando:  string; 
	public  idStatoAttuale: number;
	public  idStatoCreditoProposto: number;
	public  percSconfinamentoDa: number;
	public  percSconfinamentoA: number;
    public  codiceFinpis: string; // New
}