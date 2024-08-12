package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class BandoLineaInterventoCertificazioneVO extends GenericVO {

	private BigDecimal idPropostaCertificaz;
	private BigDecimal idTipoLineaIntervento;
	private BigDecimal idLineaDiIntervento;
	private String codIgrueT13T14;
  	private BigDecimal idProcesso;
	private Date dtInizioValidita;
  	private BigDecimal idTipoStrumentoProgr;
  	private BigDecimal numDelibera;
  	private BigDecimal idStrumentoAttuativo;
  	private BigDecimal idLineaDiInterventoPadre;
	private BigDecimal annoDelibera;
	private Date dtFineValidita;
  	private String codLivGerarchico;
	private String descBreveLinea;
	private String descLinea;
  	private String flagCampionRilev;

	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

	    temp = DataFilter.removeNull( codIgrueT13T14);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT13T14: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcesso: " + temp + "\t\n");
	    
		temp = DataFilter.removeNull( dtInizioValidita);
		if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idTipoStrumentoProgr);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStrumentoProgr: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numDelibera);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numDelibera: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiInterventoPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiInterventoPadre: " + temp + "\t\n");

	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
		
		temp = DataFilter.removeNull( annoDelibera);
		if (!DataFilter.isEmpty(temp)) sb.append(" annoDelibera: " + temp + "\t\n");

		temp = DataFilter.removeNull( dtFineValidita);
		if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");

		temp = DataFilter.removeNull( idTipoLineaIntervento);
		if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLineaIntervento: " + temp + "\t\n");

		temp = DataFilter.removeNull( idLineaDiIntervento);
		if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");

	    temp = DataFilter.removeNull( codLivGerarchico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codLivGerarchico: " + temp + "\t\n");

		temp = DataFilter.removeNull( descBreveLinea);
		if (!DataFilter.isEmpty(temp)) sb.append(" descBreveLinea: " + temp + "\t\n");

		temp = DataFilter.removeNull( descLinea);
		if (!DataFilter.isEmpty(temp)) sb.append(" descLinea: " + temp + "\t\n");

	    temp = DataFilter.removeNull( flagCampionRilev);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCampionRilev: " + temp + "\t\n");

		return sb.toString();
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public BigDecimal getAnnoDelibera() {
		return annoDelibera;
	}

	public void setAnnoDelibera(BigDecimal annoDelibera) {
		this.annoDelibera = annoDelibera;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public BigDecimal getIdTipoLineaIntervento() {
		return idTipoLineaIntervento;
	}

	public void setIdTipoLineaIntervento(BigDecimal idTipoLineaIntervento) {
		this.idTipoLineaIntervento = idTipoLineaIntervento;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public String getDescBreveLinea() {
		return descBreveLinea;
	}

	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}

	public String getDescLinea() {
		return descLinea;
	}

	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public String getCodIgrueT13T14() {
		return codIgrueT13T14;
	}

	public void setCodIgrueT13T14(String codIgrueT13T14) {
		this.codIgrueT13T14 = codIgrueT13T14;
	}

	public BigDecimal getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}

	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}

	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}

	public BigDecimal getNumDelibera() {
		return numDelibera;
	}

	public void setNumDelibera(BigDecimal numDelibera) {
		this.numDelibera = numDelibera;
	}

	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}

	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}

	public BigDecimal getIdLineaDiInterventoPadre() {
		return idLineaDiInterventoPadre;
	}

	public void setIdLineaDiInterventoPadre(BigDecimal idLineaDiInterventoPadre) {
		this.idLineaDiInterventoPadre = idLineaDiInterventoPadre;
	}

	public String getCodLivGerarchico() {
		return codLivGerarchico;
	}

	public void setCodLivGerarchico(String codLivGerarchico) {
		this.codLivGerarchico = codLivGerarchico;
	}

	public String getFlagCampionRilev() {
		return flagCampionRilev;
	}

	public void setFlagCampionRilev(String flagCampionRilev) {
		this.flagCampionRilev = flagCampionRilev;
	}

	
}
