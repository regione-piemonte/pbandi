/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class DatiBeneficiarioVO {

	//Dati del soggetto fornitore (testata)
	private BeneficiarioVO[] beneficiario; 
	private FornitoreVO fornitore;

	public BeneficiarioVO[] getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(BeneficiarioVO[] beneficiario) {
		this.beneficiario = beneficiario;
	}

	public FornitoreVO getFornitore() {
		return fornitore;
	}
	public void setFornitore(FornitoreVO fornitore) {
		this.fornitore = fornitore;
	}

}
