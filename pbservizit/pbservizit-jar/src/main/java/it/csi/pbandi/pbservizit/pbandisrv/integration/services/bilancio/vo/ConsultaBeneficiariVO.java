/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class ConsultaBeneficiariVO extends BaseVO {

	//parametri di input del servizio consultaBeneficiari
	private String codFisc;
	private String partIva;
	private String ragSoc;
	private Integer maxRec;
	private BeneficiarioKeyVO beneficiarioKeys;	
	//Output del servizio consultaBeneficiari.
	private DatiBeneficiarioVO[] beneficiario; 
	
	public BeneficiarioKeyVO getBeneficiarioKeys() {
		return beneficiarioKeys;
	}

	public void setBeneficiarioKeys(BeneficiarioKeyVO beneficiarioKeys) {
		this.beneficiarioKeys = beneficiarioKeys;
	}

	public DatiBeneficiarioVO[] getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(DatiBeneficiarioVO[] beneficiario) {
		this.beneficiario = beneficiario;
	}

	
	public Integer getMaxRec() {
		return maxRec;
	}
	
	public void setMaxRec(Integer maxRec) {
		this.maxRec = maxRec;
	}
	
	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getPartIva() {
		return partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}

	public String getRagSoc() {
		return ragSoc;
	}

	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}

}