/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;


public class ConsultaAttiLiquidazioneVO extends BaseVO {

	private ConsultaAttoLiquidazioneVO[] consultaAtto;


	public ConsultaAttoLiquidazioneVO[] getConsultaAtto() {
		return consultaAtto;
	}

	public void setConsultaAtto(ConsultaAttoLiquidazioneVO[] consultaAtto) {
		this.consultaAtto = consultaAtto;
	}

}
