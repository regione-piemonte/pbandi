
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDDispComunitariaVO extends GenericVO {

  	
  	private String descBreveDispComunitaria;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idDispComunitaria;
  	
  	private String descDispComunitaria;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDDispComunitariaVO() {}
  	
	public PbandiDDispComunitariaVO (BigDecimal idDispComunitaria) {
	
		this. idDispComunitaria =  idDispComunitaria;
	}
  	
	public PbandiDDispComunitariaVO (String descBreveDispComunitaria, Date dtFineValidita, BigDecimal idDispComunitaria, String descDispComunitaria, Date dtInizioValidita) {
	
		this. descBreveDispComunitaria =  descBreveDispComunitaria;
		this. dtFineValidita =  dtFineValidita;
		this. idDispComunitaria =  idDispComunitaria;
		this. descDispComunitaria =  descDispComunitaria;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveDispComunitaria() {
		return descBreveDispComunitaria;
	}
	
	public void setDescBreveDispComunitaria(String descBreveDispComunitaria) {
		this.descBreveDispComunitaria = descBreveDispComunitaria;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdDispComunitaria() {
		return idDispComunitaria;
	}
	
	public void setIdDispComunitaria(BigDecimal idDispComunitaria) {
		this.idDispComunitaria = idDispComunitaria;
	}
	
	public String getDescDispComunitaria() {
		return descDispComunitaria;
	}
	
	public void setDescDispComunitaria(String descDispComunitaria) {
		this.descDispComunitaria = descDispComunitaria;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDispComunitaria != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveDispComunitaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveDispComunitaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDispComunitaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDispComunitaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descDispComunitaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descDispComunitaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDispComunitaria");
		
	    return pk;
	}
	
	
}
