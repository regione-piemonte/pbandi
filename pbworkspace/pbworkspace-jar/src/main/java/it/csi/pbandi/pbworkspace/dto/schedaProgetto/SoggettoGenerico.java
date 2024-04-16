/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SoggettoGenerico implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPF datiPF = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPG datiPG = null;
	private java.lang.String flagPersonaFisica = null;

	public SoggettoGenerico() {
		super();
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPF getDatiPF() {
		return datiPF;
	}

	public void setDatiPF(it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPF datiPF) {
		this.datiPF = datiPF;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPG getDatiPG() {
		return datiPG;
	}

	public void setDatiPG(it.csi.pbandi.pbworkspace.dto.schedaProgetto.SoggettoPG datiPG) {
		this.datiPG = datiPG;
	}

	public java.lang.String getFlagPersonaFisica() {
		return flagPersonaFisica;
	}

	public void setFlagPersonaFisica(java.lang.String flagPersonaFisica) {
		this.flagPersonaFisica = flagPersonaFisica;
	}

}
