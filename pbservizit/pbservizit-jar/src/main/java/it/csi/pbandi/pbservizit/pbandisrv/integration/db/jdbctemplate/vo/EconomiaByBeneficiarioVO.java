/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEconomieVO;

public class EconomiaByBeneficiarioVO extends EconomiaVO {
	
	public String denominazioneEnteGiuridico;

	public String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}

	public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
		this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
	}
	
}
