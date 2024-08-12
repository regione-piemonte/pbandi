
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTStoricoAccessoVO extends GenericVO {

  	
  	private Date dtAccesso;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idTipoAccesso;
  	
  	private BigDecimal idStoricoAccesso;
  	
	public PbandiTStoricoAccessoVO() {}
  	
	public PbandiTStoricoAccessoVO (BigDecimal idStoricoAccesso) {
	
		this. idStoricoAccesso =  idStoricoAccesso;
	}
  	
	public PbandiTStoricoAccessoVO (Date dtAccesso, BigDecimal idUtente, BigDecimal idTipoAccesso, BigDecimal idStoricoAccesso) {
	
		this. dtAccesso =  dtAccesso;
		this. idUtente =  idUtente;
		this. idTipoAccesso =  idTipoAccesso;
		this. idStoricoAccesso =  idStoricoAccesso;
	}
  	
  	
	public Date getDtAccesso() {
		return dtAccesso;
	}
	
	public void setDtAccesso(Date dtAccesso) {
		this.dtAccesso = dtAccesso;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public BigDecimal getIdTipoAccesso() {
		return idTipoAccesso;
	}
	
	public void setIdTipoAccesso(BigDecimal idTipoAccesso) {
		this.idTipoAccesso = idTipoAccesso;
	}
	
	public BigDecimal getIdStoricoAccesso() {
		return idStoricoAccesso;
	}
	
	public void setIdStoricoAccesso(BigDecimal idStoricoAccesso) {
		this.idStoricoAccesso = idStoricoAccesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtente != null && idTipoAccesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStoricoAccesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStoricoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStoricoAccesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStoricoAccesso");
		
	    return pk;
	}
	
	
}
