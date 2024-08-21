/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDCaricaVO extends GenericVO {

  	
  	private String descEstesaCarica;
  	
  	private String descBreveCarica;
  	
  	private BigDecimal idCarica;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDCaricaVO() {}
  	
	public PbandiDCaricaVO (BigDecimal idCarica) {
	
		this. idCarica =  idCarica;
	}
  	
	public PbandiDCaricaVO (String descEstesaCarica, String descBreveCarica, BigDecimal idCarica, Date dtFineValidita, Date dtInizioValidita) {
	
		this. descEstesaCarica =  descEstesaCarica;
		this. descBreveCarica =  descBreveCarica;
		this. idCarica =  idCarica;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescEstesaCarica() {
		return descEstesaCarica;
	}
	
	public void setDescEstesaCarica(String descEstesaCarica) {
		this.descEstesaCarica = descEstesaCarica;
	}
	
	public String getDescBreveCarica() {
		return descBreveCarica;
	}
	
	public void setDescBreveCarica(String descBreveCarica) {
		this.descBreveCarica = descBreveCarica;
	}
	
	public BigDecimal getIdCarica() {
		return idCarica;
	}
	
	public void setIdCarica(BigDecimal idCarica) {
		this.idCarica = idCarica;
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descEstesaCarica != null && descBreveCarica != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCarica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descEstesaCarica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descEstesaCarica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveCarica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveCarica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCarica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCarica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCarica");
		
	    return pk;
	}
	
	
}
