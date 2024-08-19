/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class InizializzaErogazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private String codiceVisualizzatoProgetto;
	private Long dimMaxFileFirmato;
	
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public Long getDimMaxFileFirmato() {
		return dimMaxFileFirmato;
	}
	public void setDimMaxFileFirmato(Long dimMaxFileFirmato) {
		this.dimMaxFileFirmato = dimMaxFileFirmato;
	}
	
}
