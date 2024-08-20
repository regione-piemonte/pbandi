/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class BloccoVO{
    public  descCausaleBlocco : string
    public  dataInserimentoBlocco: Date;
    public  dataInserimentoSblocco: Date;
    public  idBlocco:  number;
    public  idSoggetto: number;
    public  idCausaleBlocco: number;
    public  idUtente: number;
    public idProgSoggDomanda: number;
    public ndg: string;
    public numeroDomanda: any;
    public  nome: string; 
	public  cognome: string;
	public  descMacroArea: string;
}
