/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaContoEconVO extends GenericVO {

  	
  	private String descBreveTipologiaContoEco;
  	
  	private String descTipologiaContoEconomico;
  	
  	private BigDecimal idTipologiaContoEconomico;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipologiaContoEconVO() {}
  	
	public PbandiDTipologiaContoEconVO (BigDecimal idTipologiaContoEconomico) {
	
		this. idTipologiaContoEconomico =  idTipologiaContoEconomico;
	}
  	
	public PbandiDTipologiaContoEconVO (String descBreveTipologiaContoEco, String descTipologiaContoEconomico, BigDecimal idTipologiaContoEconomico, Date dtFineValidita, Date dtInizioValidita) {
	
		this. descBreveTipologiaContoEco =  descBreveTipologiaContoEco;
		this. descTipologiaContoEconomico =  descTipologiaContoEconomico;
		this. idTipologiaContoEconomico =  idTipologiaContoEconomico;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveTipologiaContoEco() {
		return descBreveTipologiaContoEco;
	}
	
	public void setDescBreveTipologiaContoEco(String descBreveTipologiaContoEco) {
		this.descBreveTipologiaContoEco = descBreveTipologiaContoEco;
	}
	
	public String getDescTipologiaContoEconomico() {
		return descTipologiaContoEconomico;
	}
	
	public void setDescTipologiaContoEconomico(String descTipologiaContoEconomico) {
		this.descTipologiaContoEconomico = descTipologiaContoEconomico;
	}
	
	public BigDecimal getIdTipologiaContoEconomico() {
		return idTipologiaContoEconomico;
	}
	
	public void setIdTipologiaContoEconomico(BigDecimal idTipologiaContoEconomico) {
		this.idTipologiaContoEconomico = idTipologiaContoEconomico;
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
                if (isPKValid() && descBreveTipologiaContoEco != null && descTipologiaContoEconomico != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaContoEconomico != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipologiaContoEco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipologiaContoEco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipologiaContoEconomico");
		
	    return pk;
	}
	
	
}
