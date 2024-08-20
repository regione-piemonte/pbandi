/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class UpdateAnagraficaBeneficiarioPfVO {
	
	private Long idSoggetto;
	
	//FLAG NECESSARIO PER IDENTIFICARE UPDATE DA INSERT
	private Boolean isUpdate;
	
	private AnagraficaBeneficiarioPfVO anagBene;
	
	

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public AnagraficaBeneficiarioPfVO getAnagBene() {
		return anagBene;
	}

	public void setAnagBene(AnagraficaBeneficiarioPfVO anagBene) {
		this.anagBene = anagBene;
	}
	

	public UpdateAnagraficaBeneficiarioPfVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String toString() {
		return "UpdateAnagraficaBeneficiarioPfVO [idSoggetto=" + idSoggetto + ", isUpdate=" + isUpdate + ", anagBene="
				+ anagBene + "]";
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	

}
