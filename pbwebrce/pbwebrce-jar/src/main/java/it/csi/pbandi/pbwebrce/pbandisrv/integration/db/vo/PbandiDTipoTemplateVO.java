/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoTemplateVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoTemplate;
  	
  	private String descTipoTemplate;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoTemplate;
  	
	public PbandiDTipoTemplateVO() {}
  	
	public PbandiDTipoTemplateVO (BigDecimal idTipoTemplate) {
	
		this. idTipoTemplate =  idTipoTemplate;
	}
  	
	public PbandiDTipoTemplateVO (Date dtFineValidita, String descBreveTipoTemplate, String descTipoTemplate, Date dtInizioValidita, BigDecimal idTipoTemplate) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoTemplate =  descBreveTipoTemplate;
		this. descTipoTemplate =  descTipoTemplate;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoTemplate =  idTipoTemplate;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoTemplate() {
		return descBreveTipoTemplate;
	}
	
	public void setDescBreveTipoTemplate(String descBreveTipoTemplate) {
		this.descBreveTipoTemplate = descBreveTipoTemplate;
	}
	
	public String getDescTipoTemplate() {
		return descTipoTemplate;
	}
	
	public void setDescTipoTemplate(String descTipoTemplate) {
		this.descTipoTemplate = descTipoTemplate;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoTemplate() {
		return idTipoTemplate;
	}
	
	public void setIdTipoTemplate(BigDecimal idTipoTemplate) {
		this.idTipoTemplate = idTipoTemplate;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoTemplate != null && descTipoTemplate != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoTemplate != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoTemplate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoTemplate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTemplate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTemplate: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
		pk.add("idTipoTemplate");
	
    return pk;
	}
	
	
	
}
