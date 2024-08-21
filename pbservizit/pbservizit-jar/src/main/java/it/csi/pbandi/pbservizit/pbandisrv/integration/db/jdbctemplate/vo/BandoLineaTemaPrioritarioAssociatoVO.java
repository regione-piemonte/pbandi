/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBanLineaIntTemPriVO;

public class BandoLineaTemaPrioritarioAssociatoVO extends
		PbandiRBanLineaIntTemPriVO {
	private String descTemaPrioritario;
	private String codIgrueT4;

	public void setDescTemaPrioritario(String descTemaPrioritario) {
		this.descTemaPrioritario = descTemaPrioritario;
	}

	public String getDescTemaPrioritario() {
		return descTemaPrioritario;
	}

	public void setCodIgrueT4(String codIgrueT4) {
		this.codIgrueT4 = codIgrueT4;
	}

	public String getCodIgrueT4() {
		return codIgrueT4;
	}

}
