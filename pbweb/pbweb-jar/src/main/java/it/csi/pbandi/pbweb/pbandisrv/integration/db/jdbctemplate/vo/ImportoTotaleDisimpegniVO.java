/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFolderFileDocIndexVO;

public class ImportoTotaleDisimpegniVO extends GenericVO {
	
	private Double importoTotaleDisimpegni;
	private BigDecimal idProgetto;
	
	public Double getImportoTotaleDisimpegni() {
		return importoTotaleDisimpegni;
	}
	public void setImportoTotaleDisimpegni(Double importoTotaleDisimpegni) {
		this.importoTotaleDisimpegni = importoTotaleDisimpegni;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
}
