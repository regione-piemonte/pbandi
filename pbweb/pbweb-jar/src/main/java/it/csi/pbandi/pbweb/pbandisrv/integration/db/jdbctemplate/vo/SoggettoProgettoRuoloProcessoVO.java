package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSoggettoVO;

public class SoggettoProgettoRuoloProcessoVO extends PbandiTSoggettoVO {
	private String codiceFiscaleBeneficiario;
	private String codiceRuolo;
	private String codiceVisualizzatoProgetto;
	private String codUtente;
	private String definizioneProcesso;
	private String denominazioneBeneficiario;
	private String descBreveTipoAnagrafica;
	private Date dtFineValidita;
	private Date dtInizioValidita;
	private String flagRappresentanteLegale;
	private String flagAggiornatoFlux;
	private String idIstanzaProcesso;
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idTipoAnagrafica;
	private BigDecimal progrSoggettoProgetto;
	private String titoloProgetto;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setDefinizioneProcesso(String definizioneProcesso) {
		this.definizioneProcesso = definizioneProcesso;
	}

	public String getDefinizioneProcesso() {
		return definizioneProcesso;
	}

	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public void setCodUtente(String codUtente) {
		this.codUtente = codUtente;
	}

	public String getCodUtente() {
		return codUtente;
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

	public void setFlagAggiornatoFlux(String flagAggiornatoFlux) {
		this.flagAggiornatoFlux = flagAggiornatoFlux;
	}

	public String getFlagAggiornatoFlux() {
		return flagAggiornatoFlux;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}

	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}

	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}

	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}

	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

}
