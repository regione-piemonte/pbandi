/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class CapitoloKeyVO {
	
	private Integer annoEsercizio;	// non valorizzare. quindi non creare set e get
	private Integer numeroCapitolo;
	private Integer numeroArticolo; // non valorizzare. quindi non creare set e get

	
	public void setNumeroCapitolo(Integer numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}

	public Integer getNumeroCapitolo() {
		return numeroCapitolo;
	}
	
	
	
}