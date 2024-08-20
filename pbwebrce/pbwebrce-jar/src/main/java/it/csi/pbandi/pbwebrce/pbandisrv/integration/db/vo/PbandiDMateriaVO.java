/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDMateriaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idMateria;
  	
  	private String descMateria;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveMateria;
  	
	public PbandiDMateriaVO() {}
  	
	public PbandiDMateriaVO (BigDecimal idMateria) {
	
		this. idMateria =  idMateria;
	}
  	
	public PbandiDMateriaVO (Date dtFineValidita, BigDecimal idMateria, String descMateria, Date dtInizioValidita, String descBreveMateria) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idMateria =  idMateria;
		this. descMateria =  descMateria;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveMateria =  descBreveMateria;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdMateria() {
		return idMateria;
	}
	
	public void setIdMateria(BigDecimal idMateria) {
		this.idMateria = idMateria;
	}
	
	public String getDescMateria() {
		return descMateria;
	}
	
	public void setDescMateria(String descMateria) {
		this.descMateria = descMateria;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveMateria() {
		return descBreveMateria;
	}
	
	public void setDescBreveMateria(String descBreveMateria) {
		this.descBreveMateria = descBreveMateria;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descMateria != null && dtInizioValidita != null && descBreveMateria != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMateria != null ) {
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
	    
	    temp = DataFilter.removeNull( idMateria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMateria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMateria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMateria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveMateria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveMateria: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idMateria");
		
	    return pk;
	}
	
	
}
