/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";
import { DichiarazioneSpesaCampionamentoVO } from "../dto/DichiarazioneSpesaVO";

export class NuovoCampionamentoVO{
    
    public idTipoCampionamento:  number; 
    public  dichiarazioneSpesa: DichiarazioneSpesaCampionamentoVO; 
    public bandi: Array<AttivitaDTO>; 
    public idUtenteIns: number;
    public descCampionamento: string; 
    public idCampionamento: number; 
    public  percEstrazione:number; 
	public  numProgettiSel: number;
}