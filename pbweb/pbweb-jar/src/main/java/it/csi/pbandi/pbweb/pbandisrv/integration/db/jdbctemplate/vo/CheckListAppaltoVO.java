package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTChecklistVO;

import java.math.BigDecimal;


public class CheckListAppaltoVO extends PbandiTChecklistVO {
	
	private BigDecimal idAppalto;
	
	public CheckListAppaltoVO(){
		super();
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

}
