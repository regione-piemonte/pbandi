/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;

public class ProgettoSoggettiPrivatiVO extends PbandiRSoggettoProgettoVO {

	private String denominazioneSoggettoPrivato;

	public void setDenominazioneSoggettoPrivato(
			String denominazioneSoggettoPrivato) {
		this.denominazioneSoggettoPrivato = denominazioneSoggettoPrivato;
	}

	public String getDenominazioneSoggettoPrivato() {
		return denominazioneSoggettoPrivato;
	}
}
