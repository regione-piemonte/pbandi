package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDettPropostaCertifVO;

public class ProgettoPerCompensazioneVO extends PbandiTDettPropostaCertifVO {
	public String getDescAsse() {
		return descAsse;
	}

	public void setDescAsse(String descAsse) {
		this.descAsse = descAsse;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	private String descAsse;
	private String codiceVisualizzato;
}
