package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class IndirizzoVO extends GenericVO {
	
	private BigDecimal idIndirizzo;
	private BigDecimal idComune;
	private String descComune;
	private BigDecimal idComuneEstero;
	private String descComuneEstero;
	private BigDecimal idNazione;
	private String descNazione;
	private BigDecimal idProvincia;
	private String siglaProvincia;
	private String descProvincia;
	private BigDecimal idRegione;
	private String descRegione;
	private String descIndirizzo;
	private String cap;
	private String civico;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	
	
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public BigDecimal getIdComune() {
		return idComune;
	}
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public BigDecimal getIdComuneEstero() {
		return idComuneEstero;
	}
	public void setIdComuneEstero(BigDecimal idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	public String getDescComuneEstero() {
		return descComuneEstero;
	}
	public void setDescComuneEstero(String descComuneEstero) {
		this.descComuneEstero = descComuneEstero;
	}
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	public String getDescNazione() {
		return descNazione;
	}
	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	
	
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	

}
