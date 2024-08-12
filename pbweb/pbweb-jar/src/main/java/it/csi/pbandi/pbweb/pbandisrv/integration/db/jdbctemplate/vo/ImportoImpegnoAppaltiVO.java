package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ImportoImpegnoAppaltiVO extends GenericVO{
	
	private BigDecimal importoImpegno;
	
	private BigDecimal idProgetto;

	public BigDecimal getImportoImpegno() {
		return importoImpegno;
	}

	public void setImportoImpegno(BigDecimal importoImpegno) {
		this.importoImpegno = importoImpegno;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
}
