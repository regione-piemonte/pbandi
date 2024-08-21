/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class BeneficiarioVO extends GenericVO{
	
	private String _denominazioneBeneficiario = null;
	private Long _idTipoSoggetto = null;

	public String getDenominazioneBeneficiario() {
		return _denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String beneficiario) {
		_denominazioneBeneficiario = beneficiario;
	}

	public Long getIdTipoSoggetto() {
		return _idTipoSoggetto;
	}

	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this._idTipoSoggetto = idTipoSoggetto;
	}
	
	
	

}
