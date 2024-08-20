/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

public class VisualizzaAllegatiVO {
	
	public VisualizzaAllegatiVO() {
		super();
		//TODO Auto-generated constructor stub
	}

	private String nomeFile;
	private int idDocIndex;

	

	public VisualizzaAllegatiVO(String nomeFile, int idDocIndex) {
		super();
		this.nomeFile = nomeFile;
		this.idDocIndex = idDocIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public int getIdDocIndex() {
		return idDocIndex;
	}

	public void setIdDocIndex(int idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	
	
	
	
	
}	