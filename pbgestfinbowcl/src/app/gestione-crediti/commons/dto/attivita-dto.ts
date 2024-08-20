/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AttivitaDTO{

    public idAttivita: number;
    public descAttivita: string; 
    public nome: string; 
	public cognome: string; 
	public idSoggetto: number; 
	public idSoggProg: number; 
    public progBandoLinea: number; 
    public dataInserimento: Date; 
    public  idDocumentoIndex: number; 
	public  idTipodocumentoIndex :number;
}