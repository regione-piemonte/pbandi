/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.security;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class BeneficiarioSec implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String denominazione = null;
	private String codiceFiscale = null;
	private Long idBeneficiario = null;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

    @Min(1L)
    @Max(999999999999999L)
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("BeneficiarioSec [");
		sb.append(" CodiceFiscale=" + getCodiceFiscale());
		sb.append(", getDenominazione=" + getDenominazione());
		sb.append(", getIdBeneficiario=" + getIdBeneficiario()+"]");

		return sb.toString();
	}
}
