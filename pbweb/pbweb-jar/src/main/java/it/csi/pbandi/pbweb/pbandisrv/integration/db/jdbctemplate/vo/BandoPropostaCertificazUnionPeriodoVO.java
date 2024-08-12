package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class BandoPropostaCertificazUnionPeriodoVO extends GenericVO {

	private BigDecimal idPropostaCertificaz;
	private String descProposta;
	private Date dtOraCreazione;
	private BigDecimal idStatoPropostaCertif;
	private BigDecimal idPeriodo;
	private BigDecimal idTipoPeriodo;
	private String descPeriodo;
	private String descPeriodoVisualizzata;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	
	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append( this.getClass().getName() + "\t\n");
		
		temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");

	    temp = DataFilter.removeNull( descProposta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descProposta: " + temp + "\t\n");
	    
		temp = DataFilter.removeNull( dtOraCreazione);
		if (!DataFilter.isEmpty(temp)) sb.append(" dtOraCreazione: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descPeriodoVisualizzata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descPeriodoVisualizzata: " + temp + "\t\n");

	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
		
		temp = DataFilter.removeNull( dtFineValidita);
		if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");

		return sb.toString();
	}
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public String getDescProposta() {
		return descProposta;
	}
	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}
	public Date getDtOraCreazione() {
		return dtOraCreazione;
	}
	public void setDtOraCreazione(Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}
	public BigDecimal getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}
	public void setIdStatoPropostaCertif(BigDecimal idStatoPropostaCertif) {
		this.idStatoPropostaCertif = idStatoPropostaCertif;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public BigDecimal getIdTipoPeriodo() {
		return idTipoPeriodo;
	}
	public void setIdTipoPeriodo(BigDecimal idTipoPeriodo) {
		this.idTipoPeriodo = idTipoPeriodo;
	}
	public String getDescPeriodo() {
		return descPeriodo;
	}
	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
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
	
	   
}
