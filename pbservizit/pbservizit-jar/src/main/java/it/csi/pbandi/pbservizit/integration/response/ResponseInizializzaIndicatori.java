/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.response;

public class ResponseInizializzaIndicatori {
	
	private Boolean esisteCFP;
	private Boolean esisteDsFinale;
	
	public Boolean getEsisteCFP() {
		return esisteCFP;
	}
	public void setEsisteCFP(Boolean esisteCFP) {
		this.esisteCFP = esisteCFP;
	}
	public Boolean getEsisteDsFinale() {
		return esisteDsFinale;
	}
	public void setEsisteDsFinale(Boolean esisteDsFinale) {
		this.esisteDsFinale = esisteDsFinale;
	}
	
}
