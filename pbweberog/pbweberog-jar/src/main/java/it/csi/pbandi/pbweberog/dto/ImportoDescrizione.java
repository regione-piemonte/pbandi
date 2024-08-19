/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto;

public class ImportoDescrizione {
	private String descBreve;
	private String descrizione;
	private Double importo;
	
	public void setDescBreve(String val) {
		descBreve = val;
	}

	public String getDescBreve() {
		return descBreve;
	}

	

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}

	

	public void setImporto(Double val) {
		importo = val;
	}

	public Double getImporto() {
		return importo;
	}

	private static final long serialVersionUID = 1L;

	public ImportoDescrizione() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
