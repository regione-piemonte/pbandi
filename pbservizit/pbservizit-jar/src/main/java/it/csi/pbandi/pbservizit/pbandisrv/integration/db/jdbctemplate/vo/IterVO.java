/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDIterVO;


public class IterVO extends PbandiDIterVO {
	
	private Long idProgetto;
	private String percImportoContrib; //PERC_IMPORTO_CONTRIB;
	
	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getPercImportoContrib() {
		return percImportoContrib;
	}

	public void setPercImportoContrib(String percImportoContrib) {
		this.percImportoContrib = percImportoContrib;
	}


}
