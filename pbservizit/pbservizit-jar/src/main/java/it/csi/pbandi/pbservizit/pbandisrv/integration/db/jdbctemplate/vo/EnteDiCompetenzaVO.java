/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;

public class EnteDiCompetenzaVO extends PbandiTEnteCompetenzaVO {

	private String descEnteDirezione;
	private String descBreveTipoEnteCompetenz;

	public void setDescEnteDirezione(String descEnteDirezione) {
		this.descEnteDirezione = descEnteDirezione;
	}

	public String getDescEnteDirezione() {
		return descEnteDirezione;
	}

	public void setDescBreveTipoEnteCompetenz(String descBreveTipoEnteCompetenz) {
		this.descBreveTipoEnteCompetenz = descBreveTipoEnteCompetenz;
	}

	public String getDescBreveTipoEnteCompetenz() {
		return descBreveTipoEnteCompetenz;
	}
}