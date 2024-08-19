/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

public class SoggettoTrasferimento implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idSoggettoBeneficiario;
	private String cfBeneficiario;
	private String denominazioneBeneficiario;

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public String getCfBeneficiario() {
		return cfBeneficiario;
	}

	public void setCfBeneficiario(String cfBeneficiario) {
		this.cfBeneficiario = cfBeneficiario;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public SoggettoTrasferimento() {
		super();
	}

	public String toString() {
		return super.toString();
	}

}
