package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;

public class BeneficiarioEnteGiuridicoVO extends PbandiTEnteGiuridicoVO {
	
	private BigDecimal idProgetto;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	

	

}
