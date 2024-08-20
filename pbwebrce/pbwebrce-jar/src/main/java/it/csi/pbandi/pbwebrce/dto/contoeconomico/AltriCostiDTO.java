/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

public class AltriCostiDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idDCeAltriCosti;
	private String descBreveCeAltriCosti;
	private String descCeAltriCosti;
	private Long idTCeAltriCosti;
	private Double impCeApprovato;
	private Double impCePropmod;

	public Long getIdDCeAltriCosti() {
		return idDCeAltriCosti;
	}

	public void setIdDCeAltriCosti(Long idDCeAltriCosti) {
		this.idDCeAltriCosti = idDCeAltriCosti;
	}

	public String getDescBreveCeAltriCosti() {
		return descBreveCeAltriCosti;
	}

	public void setDescBreveCeAltriCosti(String descBreveCeAltriCosti) {
		this.descBreveCeAltriCosti = descBreveCeAltriCosti;
	}

	public String getDescCeAltriCosti() {
		return descCeAltriCosti;
	}

	public void setDescCeAltriCosti(String descCeAltriCosti) {
		this.descCeAltriCosti = descCeAltriCosti;
	}

	public Long getIdTCeAltriCosti() {
		return idTCeAltriCosti;
	}

	public void setIdTCeAltriCosti(Long idTCeAltriCosti) {
		this.idTCeAltriCosti = idTCeAltriCosti;
	}

	public Double getImpCeApprovato() {
		return impCeApprovato;
	}

	public void setImpCeApprovato(Double impCeApprovato) {
		this.impCeApprovato = impCeApprovato;
	}

	public Double getImpCePropmod() {
		return impCePropmod;
	}

	public void setImpCePropmod(Double impCePropmod) {
		this.impCePropmod = impCePropmod;
	}

	@Override
	public String toString() {
		return "AltriCostiDTO [idDCeAltriCosti=" + idDCeAltriCosti + ", descBreveCeAltriCosti=" + descBreveCeAltriCosti
				+ ", descCeAltriCosti=" + descCeAltriCosti + ", idTCeAltriCosti=" + idTCeAltriCosti
				+ ", impCeApprovato=" + impCeApprovato + ", impCePropmod=" + impCePropmod + "]";
	}

}
