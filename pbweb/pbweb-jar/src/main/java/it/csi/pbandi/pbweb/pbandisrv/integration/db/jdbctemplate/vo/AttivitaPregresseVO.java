/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWAttivitaPregresseVO;

public class AttivitaPregresseVO extends PbandiWAttivitaPregresseVO {
	
	private String nomeColonna;
	private String nomeEtichetta;
	
	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}
	public String getNomeColonna() {
		return nomeColonna;
	}
	public void setNomeEtichetta(String nomeEtichetta) {
		this.nomeEtichetta = nomeEtichetta;
	}
	public String getNomeEtichetta() {
		return nomeEtichetta;
	}


}
