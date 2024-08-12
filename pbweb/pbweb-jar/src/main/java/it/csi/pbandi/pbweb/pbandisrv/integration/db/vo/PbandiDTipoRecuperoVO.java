
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoRecuperoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoRecupero;
  	
  	private String descTipoRecupero;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoRecupero;
  	
	public PbandiDTipoRecuperoVO() {}
  	
	public PbandiDTipoRecuperoVO (BigDecimal idTipoRecupero) {
	
		this. idTipoRecupero =  idTipoRecupero;
	}
  	
	public PbandiDTipoRecuperoVO (Date dtFineValidita, String descBreveTipoRecupero, String descTipoRecupero, Date dtInizioValidita, BigDecimal idTipoRecupero) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoRecupero =  descBreveTipoRecupero;
		this. descTipoRecupero =  descTipoRecupero;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoRecupero =  idTipoRecupero;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoRecupero() {
		return descBreveTipoRecupero;
	}
	
	public void setDescBreveTipoRecupero(String descBreveTipoRecupero) {
		this.descBreveTipoRecupero = descBreveTipoRecupero;
	}
	
	public String getDescTipoRecupero() {
		return descTipoRecupero;
	}
	
	public void setDescTipoRecupero(String descTipoRecupero) {
		this.descTipoRecupero = descTipoRecupero;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoRecupero() {
		return idTipoRecupero;
	}
	
	public void setIdTipoRecupero(BigDecimal idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoRecupero != null && descTipoRecupero != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoRecupero != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRecupero: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoRecupero");
		
	    return pk;
	}
	
	
}
