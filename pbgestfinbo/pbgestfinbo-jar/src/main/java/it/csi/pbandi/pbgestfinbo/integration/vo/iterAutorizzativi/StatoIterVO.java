/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

public class StatoIterVO {
	public StatoIterVO() {
		super();
	}
	    
	private String descStatoIter;
	private int idStatoIter; 
	
	public int getIdStatoIter() {
		return idStatoIter;
	}

	public void setIdStatoIter(int idStatoIter) {
		this.idStatoIter = idStatoIter;
	}

	public String getDescStatoIter() {
		return descStatoIter;
	}

	public void setDescStatoIter(String descStatoIter) {
		this.descStatoIter = descStatoIter;
	}


	
	
}
