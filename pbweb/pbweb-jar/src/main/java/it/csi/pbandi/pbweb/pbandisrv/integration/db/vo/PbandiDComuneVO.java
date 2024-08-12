
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDComuneVO extends GenericVO {

  	
  	private String cap;
  	
  	private BigDecimal idProvincia;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idComune;
  	
  	private String codIstatComune;
  	
  	private String descComune;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idDimensioneTerritor;
  	
	public PbandiDComuneVO() {}
  	
	public PbandiDComuneVO (BigDecimal idComune) {
	
		this. idComune =  idComune;
	}
  	
	public PbandiDComuneVO (String cap, BigDecimal idProvincia, Date dtFineValidita, BigDecimal idComune, String codIstatComune, String descComune, Date dtInizioValidita, BigDecimal idDimensioneTerritor) {
	
		this. cap =  cap;
		this. idProvincia =  idProvincia;
		this. dtFineValidita =  dtFineValidita;
		this. idComune =  idComune;
		this. codIstatComune =  codIstatComune;
		this. descComune =  descComune;
		this. dtInizioValidita =  dtInizioValidita;
		this. idDimensioneTerritor =  idDimensioneTerritor;
	}
  	
  	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdComune() {
		return idComune;
	}
	
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	
	public String getCodIstatComune() {
		return codIstatComune;
	}
	
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}
	
	public String getDescComune() {
		return descComune;
	}
	
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idProvincia != null && descComune != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idComune != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( cap);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cap: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIstatComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIstatComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneTerritor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneTerritor: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idComune");
		
	    return pk;
	}
	
	
}
