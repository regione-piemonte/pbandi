/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaVarazioneStatoCreditoDTO{
    
	public idVariazStatoCredito: number; 
	public idSoggProgStatoCred: number;
	public progrSoggProgetto: number; 
	public nome: string;
	public cognome: string;
	public titoloBando: string; 
	public codiceFiscale: string; 
	public descStatoCredFin: string;
	public dataProposta: Date; 
	public nag: string; 
	public idSoggetto: number;
	public idProgetto: number;
	public descNuovoStatoCred: string;
	public descStatoProposta: string;
	public idStatoPropVariazCred: number;
	public partitaIva: string;
	public descStatAzienda: string; 
	public giorniSconf: number;
	public percSconf: number;
	public impSconfCapitale: number;
	public impSconfInteressi: number; 
	public impSconfAgev: number;
	public descModalitaAgevolaz:  string;
	public denominazione:  string;
    public idStatoCreditoProposto: number;
    public idStatoCreditoAttuale: number;
    public statoCreditoProposto:  string;
    public statoCreditoAttuale:  string;
    public codiceVisualizzatoProgetto: number;
    public ndg:  string;
    public confirmable: boolean;
	public idModalitaAgevolazione: number;
	public idModalitaAgevolazioneRif: number;
	public codiceFondoFinpis: string;
	public flagAccettatoRifiutato: string;
	
}