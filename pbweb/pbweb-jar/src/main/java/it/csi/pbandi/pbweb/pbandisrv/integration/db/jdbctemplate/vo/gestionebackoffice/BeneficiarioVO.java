/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class BeneficiarioVO extends GenericVO {
	
	private Long _idSoggettoBeneficiario = null;
	private String _beneficiario = null;
	
	public Long getIdSoggettoBeneficiario() {
		return _idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this._idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getBeneficiario() {
		return _beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this._beneficiario = beneficiario;
	}
	
	

}
