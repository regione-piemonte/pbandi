/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EstremiBancariDTO } from "./estremi-bancari-DTO";
import { ModalitaAgevolazioneDTO } from "./modalita-agevolazione-DTO";

export class ModAgevEstremiBancariDTO { 
    
	public modalitaAgevolazione : ModalitaAgevolazioneDTO;

	public estremiBancariList : Array<EstremiBancariDTO>

    

}