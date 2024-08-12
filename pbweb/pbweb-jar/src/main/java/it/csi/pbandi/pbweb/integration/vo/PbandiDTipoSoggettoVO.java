package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiDTipoSoggettoVO {

  	private BigDecimal idTipoSoggetto;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoSoggetto;
  	
  	private String descTipoSoggetto;
  	
	public PbandiDTipoSoggettoVO() {}
  	
	public PbandiDTipoSoggettoVO (BigDecimal idTipoSoggetto) {
	
		this. idTipoSoggetto =  idTipoSoggetto;
	}
  	
	public PbandiDTipoSoggettoVO (BigDecimal idTipoSoggetto, Date dtFineValidita, Date dtInizioValidita, String descBreveTipoSoggetto, String descTipoSoggetto) {
	
		this. idTipoSoggetto =  idTipoSoggetto;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoSoggetto =  descBreveTipoSoggetto;
		this. descTipoSoggetto =  descTipoSoggetto;
	}
  	
  	
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoSoggetto() {
		return descBreveTipoSoggetto;
	}
	
	public void setDescBreveTipoSoggetto(String descBreveTipoSoggetto) {
		this.descBreveTipoSoggetto = descBreveTipoSoggetto;
	}
	
	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}
	
	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    sb.append(" idTipoSoggetto: " + idTipoSoggetto + "\t\n");
	    sb.append(" descTipoSoggetto: " + descTipoSoggetto + "\t\n");
	    sb.append(" descBreveTipoSoggetto: " + descBreveTipoSoggetto + "\t\n");
	    sb.append(" dtFineValidita: " + dtFineValidita + "\t\n");
	    sb.append(" dtInizioValidita: " + dtInizioValidita + "\t\n");	    
	    return sb.toString();
	}
	

}
