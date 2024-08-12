package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class TracciamentoApplicativoVO extends GenericVO {

	Timestamp tsTracciamento;
	BigDecimal idTracciamento;
	String codTracciamento;
	BigDecimal idUtente;
	String cognomeNomeCodice;
	Date dtTracciamento;
	String ttTracciamento;
	String descLogica;
	String flagEsito;
	String messaggioErrore;
	String codiceErrore;
	BigDecimal durata;
	String tipo;

	public Timestamp getTsTracciamento() {
		return tsTracciamento;
	}

	public void setTsTracciamento(Timestamp tsTracciamento) {
		this.tsTracciamento = tsTracciamento;
	}

	public BigDecimal getIdTracciamento() {
		return idTracciamento;
	}

	public void setIdTracciamento(BigDecimal idTracciamento) {
		this.idTracciamento = idTracciamento;
	}

	public String getCodTracciamento() {
		return codTracciamento;
	}

	public void setCodTracciamento(String codTracciamento) {
		this.codTracciamento = codTracciamento;
	}

	public BigDecimal getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}

	public String getCognomeNomeCodice() {
		return cognomeNomeCodice;
	}

	public void setCognomeNomeCodice(String cognomeNomeCodice) {
		this.cognomeNomeCodice = cognomeNomeCodice;
	}

	public Date getDtTracciamento() {
		return dtTracciamento;
	}

	public void setDtTracciamento(Date dtTracciamento) {
		this.dtTracciamento = dtTracciamento;
	}

	public String getTtTracciamento() {
		return ttTracciamento;
	}

	public void setTtTracciamento(String ttTracciamento) {
		this.ttTracciamento = ttTracciamento;
	}

	public String getDescLogica() {
		return descLogica;
	}

	public void setDescLogica(String descLogica) {
		this.descLogica = descLogica;
	}

	public String getFlagEsito() {
		return flagEsito;
	}

	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public BigDecimal getDurata() {
		return durata;
	}

	public void setDurata(BigDecimal durata) {
		this.durata = durata;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
