/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;

public class DatiCumuloDeMinimis implements Serializable{
	private String cumulo;
	private Double importoDisponibile;
	private Double importoSuperato;
	
	public void setCumulo(String val) {
		cumulo = val;
	}

	public String getCumulo() {
		return cumulo;
	}



	public void setImportoDisponibile(Double val) {
		importoDisponibile = val;
	}

	public Double getImportoDisponibile() {
		return importoDisponibile;
	}



	public void setImportoSuperato(Double val) {
		importoSuperato = val;
	}

	public Double getImportoSuperato() {
		return importoSuperato;
	}

	private static final long serialVersionUID = 1L;

	public DatiCumuloDeMinimis() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
