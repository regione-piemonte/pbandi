package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;

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