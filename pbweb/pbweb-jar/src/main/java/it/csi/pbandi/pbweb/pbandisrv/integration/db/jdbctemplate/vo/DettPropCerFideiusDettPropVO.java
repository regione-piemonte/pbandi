package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDettPropCerFideiusVO;

import java.math.BigDecimal;

public class DettPropCerFideiusDettPropVO extends PbandiRDettPropCerFideiusVO {
	private BigDecimal idPropostaCertificaz;

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

}
