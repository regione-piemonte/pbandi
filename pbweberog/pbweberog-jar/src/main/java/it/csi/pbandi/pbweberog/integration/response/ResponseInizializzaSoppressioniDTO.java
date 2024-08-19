/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.response;

import java.io.Serializable;
import java.util.ArrayList;
import it.csi.pbandi.pbweberog.dto.recupero.Soppressione;

public class ResponseInizializzaSoppressioniDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Soppressione> soppressioni;
	private Double importoCertificabileLordo;
	private Double importoTotaleDisimpegni;
	private Boolean insModDelAbilitati;

	public Boolean getInsModDelAbilitati() {
		return insModDelAbilitati;
	}
	public void setInsModDelAbilitati(Boolean insModDelAbilitati) {
		this.insModDelAbilitati = insModDelAbilitati;
	}
	public Double getImportoTotaleDisimpegni() {
		return importoTotaleDisimpegni;
	}
	public void setImportoTotaleDisimpegni(Double importoTotaleDisimpegni) {
		this.importoTotaleDisimpegni = importoTotaleDisimpegni;
	}
	public Double getImportoCertificabileLordo() {
		return importoCertificabileLordo;
	}
	public void setImportoCertificabileLordo(Double importoCertificabileLordo) {
		this.importoCertificabileLordo = importoCertificabileLordo;
	}
	public ArrayList<Soppressione> getSoppressioni() {
		return soppressioni;
	}
	public void setSoppressioni(ArrayList<Soppressione> soppressioni) {
		this.soppressioni = soppressioni;
	}
}
