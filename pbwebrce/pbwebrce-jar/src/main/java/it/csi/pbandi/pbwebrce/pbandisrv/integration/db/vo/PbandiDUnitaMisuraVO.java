/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDUnitaMisuraVO extends GenericVO {

  	
  	private String descUnitaMisura;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUnitaMisura;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveUnitaMisura;
  	
	public PbandiDUnitaMisuraVO() {}
  	
	public PbandiDUnitaMisuraVO (BigDecimal idUnitaMisura) {
	
		this. idUnitaMisura =  idUnitaMisura;
	}
  	
	public PbandiDUnitaMisuraVO (String descUnitaMisura, Date dtFineValidita, BigDecimal idUnitaMisura, Date dtInizioValidita, String descBreveUnitaMisura) {
	
		this. descUnitaMisura =  descUnitaMisura;
		this. dtFineValidita =  dtFineValidita;
		this. idUnitaMisura =  idUnitaMisura;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveUnitaMisura =  descBreveUnitaMisura;
	}
  	
  	
	public String getDescUnitaMisura() {
		return descUnitaMisura;
	}
	
	public void setDescUnitaMisura(String descUnitaMisura) {
		this.descUnitaMisura = descUnitaMisura;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUnitaMisura() {
		return idUnitaMisura;
	}
	
	public void setIdUnitaMisura(BigDecimal idUnitaMisura) {
		this.idUnitaMisura = idUnitaMisura;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveUnitaMisura() {
		return descBreveUnitaMisura;
	}
	
	public void setDescBreveUnitaMisura(String descBreveUnitaMisura) {
		this.descBreveUnitaMisura = descBreveUnitaMisura;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descUnitaMisura != null && dtInizioValidita != null && descBreveUnitaMisura != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idUnitaMisura != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descUnitaMisura);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descUnitaMisura: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUnitaMisura);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUnitaMisura: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveUnitaMisura);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveUnitaMisura: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idUnitaMisura");
		
	    return pk;
	}
	
	
}
