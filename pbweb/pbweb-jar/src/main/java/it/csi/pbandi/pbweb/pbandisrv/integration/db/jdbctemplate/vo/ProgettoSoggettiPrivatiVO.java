package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;

public class ProgettoSoggettiPrivatiVO extends PbandiRSoggettoProgettoVO {

	private String denominazioneSoggettoPrivato;

	public void setDenominazioneSoggettoPrivato(
			String denominazioneSoggettoPrivato) {
		this.denominazioneSoggettoPrivato = denominazioneSoggettoPrivato;
	}

	public String getDenominazioneSoggettoPrivato() {
		return denominazioneSoggettoPrivato;
	}
}
