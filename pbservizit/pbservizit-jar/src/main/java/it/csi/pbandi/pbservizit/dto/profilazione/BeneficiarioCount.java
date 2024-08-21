/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.profilazione;

import it.csi.pbandi.pbservizit.security.BeneficiarioSec;

public class BeneficiarioCount implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long count;
	private BeneficiarioSec beneficiario;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public BeneficiarioSec getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(BeneficiarioSec beneficiario) {
		this.beneficiario = beneficiario;
	}

	@Override
	public String toString() {
		return "BeneficiarioCount [beneficiario=" + beneficiario + "]";
	}

}
