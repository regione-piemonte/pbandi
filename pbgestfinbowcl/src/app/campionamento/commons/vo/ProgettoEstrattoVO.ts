/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoEstrattoVO{
    public  idSoggetto: number; 
	public  descTipoSede : string; 
	public  sedeIntervento :  string; 
	public  codiceFiscaleSoggetto:  string; 
	public  denominazione : string; 
	public  comuneSedeLegale : string; 
	public  provSedeLegale: string; 
	public  comuneSedeIntervento: string; 
	public  provSedeIntervento: string;
	public  idProgetto : number; 
	public  descBando: string;; 
	public  idBando: number; 
	public  descFormaGiuridica: string; 
	public  dataUltimoControllo: Date;
	public  idFormaGiuridica: number;
	public  motivazione: string;
	public idCampionamento: number; 
	public codVisualizzato: string;
}