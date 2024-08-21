/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoBeneficiarioVO extends GenericVO {

  	
  	private String descTipoBeneficiario;
  	
  	private String descBreveTipoBeneficiario;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoBeneficiario;
  	
	public PbandiDTipoBeneficiarioVO() {}
  	
	public PbandiDTipoBeneficiarioVO (BigDecimal idTipoBeneficiario) {
	
		this. idTipoBeneficiario =  idTipoBeneficiario;
	}
  	
	public PbandiDTipoBeneficiarioVO (String descTipoBeneficiario, String descBreveTipoBeneficiario, Date dtFineValidita, Date dtInizioValidita, BigDecimal idTipoBeneficiario) {
	
		this. descTipoBeneficiario =  descTipoBeneficiario;
		this. descBreveTipoBeneficiario =  descBreveTipoBeneficiario;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoBeneficiario =  idTipoBeneficiario;
	}
  	
  	
	public String getDescTipoBeneficiario() {
		return descTipoBeneficiario;
	}
	
	public void setDescTipoBeneficiario(String descTipoBeneficiario) {
		this.descTipoBeneficiario = descTipoBeneficiario;
	}
	
	public String getDescBreveTipoBeneficiario() {
		return descBreveTipoBeneficiario;
	}
	
	public void setDescBreveTipoBeneficiario(String descBreveTipoBeneficiario) {
		this.descBreveTipoBeneficiario = descBreveTipoBeneficiario;
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
	
	public BigDecimal getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}
	
	public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoBeneficiario != null && descBreveTipoBeneficiario != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoBeneficiario != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoBeneficiario: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoBeneficiario");
		
	    return pk;
	}
	
	
}
