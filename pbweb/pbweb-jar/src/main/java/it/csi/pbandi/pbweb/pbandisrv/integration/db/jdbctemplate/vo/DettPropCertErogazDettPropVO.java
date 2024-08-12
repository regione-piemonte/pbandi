package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDettPropCertErogazVO;

public class DettPropCertErogazDettPropVO extends PbandiRDettPropCertErogazVO {
	private BigDecimal idPropostaCertificaz;

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

}
