/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AltriDatiDsan {
    constructor(
        public dataEmissioneDsan: Date,
        public dataScadenza: Date,
        public numeroProtocollo: string,
        public descEsitoRichieste: string,
        	// dati del documento 
	    public  nomeDocumento:  string,
	    public  idDocumentoIndex: number, 
	    public  idTipoDocumentoIndex: number,
    ) { }
}