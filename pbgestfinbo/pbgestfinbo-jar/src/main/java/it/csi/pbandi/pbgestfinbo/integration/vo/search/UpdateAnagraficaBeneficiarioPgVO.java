/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;


public class UpdateAnagraficaBeneficiarioPgVO {
	
	private Long idSoggetto;
	
	//FLAG NECESSARIO PER IDENTIFICARE UPDATE DA INSERT
	private Boolean isUpdate;
		
	private AnagraficaBeneficiarioVO anagBene;
	
	


	
	

	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public AnagraficaBeneficiarioVO getAnagBene() {
		return anagBene;
	}


	public void setAnagBene(AnagraficaBeneficiarioVO anagBene) {
		this.anagBene = anagBene;
	}


	public UpdateAnagraficaBeneficiarioPgVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	

	@Override
	public String toString() {
		return "UpdateAnagraficaBeneficiarioPgVO [idSoggetto=" + idSoggetto + ", anagBene=" + anagBene + ", isUpdate=" + isUpdate + "]";
	}
	
	

}
