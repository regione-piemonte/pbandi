package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFolderFileDocIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEstremiBancariVO;

public class EstremiBancariVO extends PbandiTEstremiBancariVO {
	
	private BigDecimal idSoggetto;
	private BigDecimal idProgetto;
	private BigDecimal progrSoggettoProgetto;
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}

	
}
