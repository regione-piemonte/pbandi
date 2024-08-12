package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TotaleQuietanzatoValidatoProgettoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoValidato;
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}

}
