/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class DatiCalcolati implements java.io.Serializable {

	private Double importoTotaleRichiesto;
	private Double importoTotaleErogato;
	private Double importoResiduoSpettante;
	private Double importoTotaleAgevolato;
	private static final long serialVersionUID = 1L;

	
	
	public Double getImportoTotaleRichiesto() {
		return importoTotaleRichiesto;
	}

	public void setImportoTotaleRichiesto(Double importoTotaleRichiesto) {
		this.importoTotaleRichiesto = importoTotaleRichiesto;
	}

	public Double getImportoTotaleErogato() {
		return importoTotaleErogato;
	}

	public void setImportoTotaleErogato(Double importoTotaleErogato) {
		this.importoTotaleErogato = importoTotaleErogato;
	}

	public Double getImportoResiduoSpettante() {
		return importoResiduoSpettante;
	}

	public void setImportoResiduoSpettante(Double importoResiduoSpettante) {
		this.importoResiduoSpettante = importoResiduoSpettante;
	}

	public Double getImportoTotaleAgevolato() {
		return importoTotaleAgevolato;
	}

	public void setImportoTotaleAgevolato(Double importoTotaleAgevolato) {
		this.importoTotaleAgevolato = importoTotaleAgevolato;
	}

	public DatiCalcolati() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
