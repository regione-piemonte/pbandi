/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class ConsultaAttoLiquidazioneVO extends BaseVO {

	private Integer maxRec;
	
	public AttoLiquidazioneVO getAttoLiquidazione() {
		return attoLiquidazione;
	}

	public void setAttoLiquidazione(AttoLiquidazioneVO attoLiquidazione) {
		this.attoLiquidazione = attoLiquidazione;
	}

	private AttoLiquidazioneVO attoLiquidazione;
	
	
	public Integer getMaxRec() {
		return maxRec;
	}

	public void setMaxRec(Integer maxRec) {
		this.maxRec = maxRec;
	}

	
}
