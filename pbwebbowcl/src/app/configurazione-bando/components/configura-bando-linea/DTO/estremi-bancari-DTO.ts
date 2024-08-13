/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EstremiContoDTO } from "./estremi-conto-DTO"

export class EstremiBancariDTO {     
	
	
	public idBanca : number
	public descBanca : String 
	
	public estremi :Array<EstremiContoDTO>
	public isSendToAmministrativoContabile : boolean

}