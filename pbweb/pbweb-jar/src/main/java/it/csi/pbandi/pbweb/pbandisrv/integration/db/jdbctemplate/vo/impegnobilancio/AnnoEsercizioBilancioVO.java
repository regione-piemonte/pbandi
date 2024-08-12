package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class AnnoEsercizioBilancioVO extends GenericVO {
	private String annoEsercizio;

	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}

	public String getAnnoEsercizio() {
		return annoEsercizio;
	}

}
