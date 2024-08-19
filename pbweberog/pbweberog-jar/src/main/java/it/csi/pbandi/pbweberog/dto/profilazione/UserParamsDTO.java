/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.profilazione;

public class UserParamsDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Long idUtente;

	public void setIdUtente(Long val) {
		idUtente = val;
	}

	
	public Long getIdUtente() {
		return idUtente;
	}

	private Long idSoggetto;

	public void setIdSoggetto(Long val) {
		idSoggetto = val;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}

	private Long idSoggettoBeneficiario;

	public void setIdSoggettoBeneficiario(Long val) {
		idSoggettoBeneficiario = val;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	private Boolean flagIncaricato;

	public void setFlagIncaricato(Boolean val) {
		flagIncaricato = val;
	}

	public Boolean getFlagIncaricato() {
		return flagIncaricato;
	}

	private String identitaIride;

	public void setIdentitaIride(String val) {
		identitaIride = val;
	}

	public String getIdentitaIride() {
		return identitaIride;
	}

	private String codTipoAnagrafica;

	public void setCodTipoAnagrafica(String val) {
		codTipoAnagrafica = val;
	}

	public String getCodTipoAnagrafica() {
		return codTipoAnagrafica;
	}
}
