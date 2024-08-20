/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class AnagAltriDati_Main {
    
	public dimImpr_numDomanda: string;
  	public dimImpr_dataValutazione: Date;
  	public dimImpr_esito: string;

  	public durc_dataEmiss: Date;
	public durc_esito: string;
	public durc_dataScadenza: Date;
	public durc_numProto: string;
  
  	public bdna_numDomanda: string;
	public bdna_dataRic: Date;
	public bdna_numProto: string;
	
	public anti_numDom: string;
	public anti_dataEmiss: Date;
	public anti_esito: string;
	public anti_dataScad: Date;
	public anti_numProto: string;
	
  	public dimImpresa: Array<AnagAltriDati_Main>;
  	public durc: Array<AnagAltriDati_Main>;
  	public bdna: Array<AnagAltriDati_Main>;
  	public antiMafia: Array<AnagAltriDati_Main>;

		// dati del documento 
	public  nomeDocumento:  string;
	public  idDocumentoIndex: number; 
	public  idTipoDocumentoIndex: number; 
}