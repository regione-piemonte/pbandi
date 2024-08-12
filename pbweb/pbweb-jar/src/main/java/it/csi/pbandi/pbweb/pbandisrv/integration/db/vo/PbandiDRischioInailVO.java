
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDRischioInailVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descRischioInail;
  	
  	private BigDecimal idRischioInail;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveRischioInail;
  	
	public PbandiDRischioInailVO() {}
  	
	public PbandiDRischioInailVO (BigDecimal idRischioInail) {
	
		this. idRischioInail =  idRischioInail;
	}
  	
	public PbandiDRischioInailVO (Date dtFineValidita, String descRischioInail, BigDecimal idRischioInail, Date dtInizioValidita, String descBreveRischioInail) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descRischioInail =  descRischioInail;
		this. idRischioInail =  idRischioInail;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveRischioInail =  descBreveRischioInail;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescRischioInail() {
		return descRischioInail;
	}
	
	public void setDescRischioInail(String descRischioInail) {
		this.descRischioInail = descRischioInail;
	}
	
	public BigDecimal getIdRischioInail() {
		return idRischioInail;
	}
	
	public void setIdRischioInail(BigDecimal idRischioInail) {
		this.idRischioInail = idRischioInail;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveRischioInail() {
		return descBreveRischioInail;
	}
	
	public void setDescBreveRischioInail(String descBreveRischioInail) {
		this.descBreveRischioInail = descBreveRischioInail;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descRischioInail != null && dtInizioValidita != null && descBreveRischioInail != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRischioInail != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descRischioInail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRischioInail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRischioInail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRischioInail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveRischioInail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveRischioInail: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRischioInail");
		
	    return pk;
	}
	
	
}
