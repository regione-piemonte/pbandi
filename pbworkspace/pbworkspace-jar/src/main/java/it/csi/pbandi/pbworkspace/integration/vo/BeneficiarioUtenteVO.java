/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.math.BigDecimal;

public class BeneficiarioUtenteVO {

	private BigDecimal idSoggettoBeneficiario;
	private String codiceFiscaleBeneficiario;
	private String denominazioneBeneficiario;
	
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	
	@Override
	public String toString() {
		return "BeneficiarioUtenteVO [idSoggettoBeneficiario=" + idSoggettoBeneficiario + ", codiceFiscaleBeneficiario="
				+ codiceFiscaleBeneficiario + ", denominazioneBeneficiario=" + denominazioneBeneficiario + "]";
	}
	
}