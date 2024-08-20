/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RichiestaProrogaVo {

    public  idRichiestaProroga:  number; 
	public  idStatoProroga: number; 
	public  descStatoProroga: string; 
	public  dataRichiesta: Date; 
	public  numGiorniRichiesta: number; 
	public  dataInserimento: Date; 
	public  motivazione : string;
    public  idTarget: number; 
    public  numGiorniApprov: number; 
}
