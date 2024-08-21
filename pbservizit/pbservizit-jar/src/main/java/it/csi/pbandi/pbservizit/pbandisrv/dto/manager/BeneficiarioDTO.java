/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager;


public class BeneficiarioDTO implements java.io.Serializable {

	
	static final long serialVersionUID = 1;

	private java.lang.String denominazioneBeneficiario = null;
	private Long idSoggetto;
	private java.lang.Long idTipoSoggetto = null;
	
	public java.lang.String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(
			java.lang.String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public java.lang.Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	public void setIdTipoSoggetto(java.lang.Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}


	
}
