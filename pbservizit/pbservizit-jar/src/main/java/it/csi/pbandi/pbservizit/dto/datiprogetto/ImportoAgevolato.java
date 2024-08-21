/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.datiprogetto;

public class ImportoAgevolato {
	private Double importo;
	private String descrizione;
	private Double importoAlNettoRevoche;
	
	public void setImporto(Double val) {
		importo = val;
	}

	public Double getImporto() {
		return importo;
	}

	
	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}	

	public void setImportoAlNettoRevoche(Double val) {
		importoAlNettoRevoche = val;
	}

	public Double getImportoAlNettoRevoche() {
		return importoAlNettoRevoche;
	}

	private static final long serialVersionUID = 1L;

	public ImportoAgevolato() {
		super();

	}

	public String toString() {

		return super.toString();
	}
}
