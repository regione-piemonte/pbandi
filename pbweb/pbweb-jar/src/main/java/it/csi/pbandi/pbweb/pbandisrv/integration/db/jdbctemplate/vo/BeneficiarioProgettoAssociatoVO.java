package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BeneficiarioProgettoAssociatoVO extends GenericVO {
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBeneficiario;
	private String codiceFiscaleBeneficiario;
	private String denominazioneBeneficiario;
	private String codiceVisualizzatoProgetto;
	private String titoloProgetto;
	private String flagRappresentanteLegale;
	private BigDecimal progrSoggettoProgetto;
	private Date dtFineValidita;
	private Date dtInizioValidita;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public void setFlagRappresentanteLegale(String flagRappresentanteLegale) {
		this.flagRappresentanteLegale = flagRappresentanteLegale;
	}

	public String getFlagRappresentanteLegale() {
		return flagRappresentanteLegale;
	}

	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}

	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

}
