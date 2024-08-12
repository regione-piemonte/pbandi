package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ProgettoSoggettoPartnerVO extends GenericVO {

	private BigDecimal idProgetto;
	private BigDecimal idSoggettoPartner;
	private BigDecimal idProgettoPartner;
	private String descSoggettoPartner;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdSoggettoPartner(BigDecimal idSoggettoPartner) {
		this.idSoggettoPartner = idSoggettoPartner;
	}

	public BigDecimal getIdSoggettoPartner() {
		return idSoggettoPartner;
	}

	public void setIdProgettoPartner(BigDecimal idProgettoPartner) {
		this.idProgettoPartner = idProgettoPartner;
	}

	public BigDecimal getIdProgettoPartner() {
		return idProgettoPartner;
	}

	public void setDescSoggettoPartner(String descSoggettoPartner) {
		this.descSoggettoPartner = descSoggettoPartner;
	}

	public String getDescSoggettoPartner() {
		return descSoggettoPartner;
	}
}
