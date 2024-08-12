
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiCRegolaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveRegola;
  	
  	private String descRegola;
  	
  	private BigDecimal idRegola;
  	
  	private Date dtInizioValidita;
  	
	public PbandiCRegolaVO() {}
  	
	public PbandiCRegolaVO (BigDecimal idRegola) {
	
		this. idRegola =  idRegola;
	}
  	
	public PbandiCRegolaVO (Date dtFineValidita, String descBreveRegola, String descRegola, BigDecimal idRegola, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveRegola =  descBreveRegola;
		this. descRegola =  descRegola;
		this. idRegola =  idRegola;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveRegola() {
		return descBreveRegola;
	}
	
	public void setDescBreveRegola(String descBreveRegola) {
		this.descBreveRegola = descBreveRegola;
	}
	
	public String getDescRegola() {
		return descRegola;
	}
	
	public void setDescRegola(String descRegola) {
		this.descRegola = descRegola;
	}
	
	public BigDecimal getIdRegola() {
		return idRegola;
	}
	
	public void setIdRegola(BigDecimal idRegola) {
		this.idRegola = idRegola;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveRegola != null && descRegola != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRegola != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveRegola);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveRegola: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descRegola);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRegola: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegola);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegola: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRegola");
		
	    return pk;
	}
	
	
}
