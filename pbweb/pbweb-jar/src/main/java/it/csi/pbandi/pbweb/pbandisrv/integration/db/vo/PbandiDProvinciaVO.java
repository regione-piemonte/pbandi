
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDProvinciaVO extends GenericVO {

  	
  	private BigDecimal idProvincia;
  	
  	private Date dtFineValidita;
  	
  	private String siglaProvincia;
  	
  	private String codIstatProvincia;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idRegione;
  	
  	private String descProvincia;
  	
	public PbandiDProvinciaVO() {}
  	
	public PbandiDProvinciaVO (BigDecimal idProvincia) {
	
		this. idProvincia =  idProvincia;
	}
  	
	public PbandiDProvinciaVO (BigDecimal idProvincia, Date dtFineValidita, String siglaProvincia, String codIstatProvincia, Date dtInizioValidita, BigDecimal idRegione, String descProvincia) {
	
		this. idProvincia =  idProvincia;
		this. dtFineValidita =  dtFineValidita;
		this. siglaProvincia =  siglaProvincia;
		this. codIstatProvincia =  codIstatProvincia;
		this. dtInizioValidita =  dtInizioValidita;
		this. idRegione =  idRegione;
		this. descProvincia =  descProvincia;
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
	
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	
	public String getCodIstatProvincia() {
		return codIstatProvincia;
	}
	
	public void setCodIstatProvincia(String codIstatProvincia) {
		this.codIstatProvincia = codIstatProvincia;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	
	public String getDescProvincia() {
		return descProvincia;
	}
	
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && siglaProvincia != null && dtInizioValidita != null && idRegione != null && descProvincia != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProvincia != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( siglaProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" siglaProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIstatProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIstatProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descProvincia: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProvincia");
		
	    return pk;
	}
	
	
}
