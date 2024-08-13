/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto;

public class IntegerValueDTO {
	
	private Integer value;

	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public boolean getBooleanValue() {
		if(value != null && value > 0)
			return true;
		
		return false;
	}

	
	

}
