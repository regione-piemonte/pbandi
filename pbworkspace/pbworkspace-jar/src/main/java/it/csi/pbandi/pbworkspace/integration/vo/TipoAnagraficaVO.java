/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.math.BigDecimal;

public class TipoAnagraficaVO extends PbandiDTipoAnagraficaVO {

	private String descRuoloHelp;
  	private BigDecimal idSoggetto;



	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getDescRuoloHelp() {
		return descRuoloHelp;
	}

	public void setDescRuoloHelp(String descRuoloHelp) {
		this.descRuoloHelp = descRuoloHelp;
	}
}
