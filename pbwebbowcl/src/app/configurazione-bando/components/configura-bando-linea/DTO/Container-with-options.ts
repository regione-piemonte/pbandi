/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContainerWithOptions {     
	
	
	public value : any
	public isEditable: boolean

	constructor(value :any, isEditable){
		this.value = value
		this.isEditable = isEditable
	}

	


	
	public static createList(oldList : Array<any>) : Array<ContainerWithOptions>{
		
		let newList : Array<ContainerWithOptions> = []
		for(let e of oldList){
			newList.push(new ContainerWithOptions(e,false))
		}

		return newList
	}



	

}