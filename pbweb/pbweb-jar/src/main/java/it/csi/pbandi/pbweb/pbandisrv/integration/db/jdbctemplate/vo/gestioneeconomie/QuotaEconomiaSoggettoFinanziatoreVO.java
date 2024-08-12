package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestioneeconomie;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;
import java.math.BigDecimal;

public class QuotaEconomiaSoggettoFinanziatoreVO extends GenericVO {
	
	private String descSoggFinanziatore;
	private BigDecimal idSoggettoFinanziatore;
	private BigDecimal idEconomia;
	private Double impQuotaEconSoggFinanziat;
	private Double percQuotaSoggFinanziat;
	private BigDecimal idProgetto;
	private String tipologiaProgetto;
	
	public QuotaEconomiaSoggettoFinanziatoreVO(){}

	public String getTipologiaProgetto() {
		return tipologiaProgetto;
	}

	public void setTipologiaProgetto(String tipologiaProgetto) {
		this.tipologiaProgetto = tipologiaProgetto;
	}


	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}

	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}

	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}

	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}

	public BigDecimal getIdEconomia() {
		return idEconomia;
	}

	public void setIdEconomia(BigDecimal idEconomia) {
		this.idEconomia = idEconomia;
	}

	public Double getImpQuotaEconSoggFinanziat() {
		return impQuotaEconSoggFinanziat;
	}

	public void setImpQuotaEconSoggFinanziat(Double impQuotaEconSoggFinanziat) {
		this.impQuotaEconSoggFinanziat = impQuotaEconSoggFinanziat;
	}

	public Double getPercQuotaSoggFinanziat() {
		return percQuotaSoggFinanziat;
	}

	public void setPercQuotaSoggFinanziat(Double percQuotaSoggFinanziat) {
		this.percQuotaSoggFinanziat = percQuotaSoggFinanziat;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}
