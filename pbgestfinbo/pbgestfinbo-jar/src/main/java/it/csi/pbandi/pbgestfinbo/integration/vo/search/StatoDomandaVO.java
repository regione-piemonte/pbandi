/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class StatoDomandaVO {
	
	private String statoDomanda;

	public String getStatoDomanda() {
		return statoDomanda;
	}

	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}
	@Override
	public String toString() {
		return "StatoDomandaVO [statoDomanda=" + statoDomanda + "]";
	}
}
