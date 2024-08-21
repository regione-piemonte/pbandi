/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAiutoVO extends GenericVO {

  	
  	private String descBreveTipoAiuto;
  	
  	private String codIgrueT1;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoAiuto;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipoAiuto;
  	
	public PbandiDTipoAiutoVO() {}
  	
	public PbandiDTipoAiutoVO (BigDecimal idTipoAiuto) {
	
		this. idTipoAiuto =  idTipoAiuto;
	}
  	
	public PbandiDTipoAiutoVO (String descBreveTipoAiuto, String codIgrueT1, Date dtFineValidita, BigDecimal idTipoAiuto, Date dtInizioValidita, String descTipoAiuto) {
	
		this. descBreveTipoAiuto =  descBreveTipoAiuto;
		this. codIgrueT1 =  codIgrueT1;
		this. dtFineValidita =  dtFineValidita;
		this. idTipoAiuto =  idTipoAiuto;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipoAiuto =  descTipoAiuto;
	}
  	
  	
	public String getDescBreveTipoAiuto() {
		return descBreveTipoAiuto;
	}
	
	public void setDescBreveTipoAiuto(String descBreveTipoAiuto) {
		this.descBreveTipoAiuto = descBreveTipoAiuto;
	}
	
	public String getCodIgrueT1() {
		return codIgrueT1;
	}
	
	public void setCodIgrueT1(String codIgrueT1) {
		this.codIgrueT1 = codIgrueT1;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipoAiuto() {
		return descTipoAiuto;
	}
	
	public void setDescTipoAiuto(String descTipoAiuto) {
		this.descTipoAiuto = descTipoAiuto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoAiuto != null && codIgrueT1 != null && dtInizioValidita != null && descTipoAiuto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAiuto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoAiuto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT1);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT1: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAiuto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAiuto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAiuto");
		
	    return pk;
	}
	
	
}
