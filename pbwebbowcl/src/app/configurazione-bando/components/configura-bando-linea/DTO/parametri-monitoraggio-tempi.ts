/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ParametriMonitoraggioTempi {     
	
	
	public idParamMonitBandoLinea : number;
	public idParametroMonit : number;
	public progrBandoLineaIntervento : number;
	public numGiorni : number;
	public dtInizioValidita : Date;
	public dtFineValidita : Date;
	public descBreveParametroMonit : string;
	public descParametroMonit : string;



	public static copy (input : ParametriMonitoraggioTempi ):ParametriMonitoraggioTempi{ 

		
		
		let output = new ParametriMonitoraggioTempi()
		output.idParamMonitBandoLinea  = input.idParamMonitBandoLinea;
		output.idParametroMonit = input.idParametroMonit;
		output.progrBandoLineaIntervento = input.progrBandoLineaIntervento;
		output.numGiorni = input.numGiorni;
		output.dtInizioValidita = input.dtInizioValidita;
		output.dtFineValidita = input.dtFineValidita;
		output.descBreveParametroMonit = input.descBreveParametroMonit;
		output.descParametroMonit = input.descParametroMonit;

		return output;
	}

}