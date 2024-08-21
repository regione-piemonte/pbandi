/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDMotivoAssenzaCigVO extends GenericVO {

  	
  	private BigDecimal idMotivoAssenzaCig;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveMotivoAssenzaCig;
  	
  	private String descMotivoAssenzaCig;
  	
  	private String tc22;
  	
	public PbandiDMotivoAssenzaCigVO() {}
  	
	public PbandiDMotivoAssenzaCigVO (BigDecimal idMotivoAssenzaCig) {
	
		this. idMotivoAssenzaCig =  idMotivoAssenzaCig;
	}
  	
	public PbandiDMotivoAssenzaCigVO (BigDecimal idMotivoAssenzaCig, Date dtFineValidita, Date dtInizioValidita, String descBreveMotivoAssenzaCig, String descMotivoAssenzaCig, String tc22) {
	
		this. idMotivoAssenzaCig =  idMotivoAssenzaCig;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveMotivoAssenzaCig =  descBreveMotivoAssenzaCig;
		this. descMotivoAssenzaCig =  descMotivoAssenzaCig;
		this. tc22 = tc22;
	}
  	
  	
	public BigDecimal getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}
	
	public void setIdMotivoAssenzaCig(BigDecimal idMotivoAssenzaCig) {
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
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
	
	public String getDescBreveMotivoAssenzaCig() {
		return descBreveMotivoAssenzaCig;
	}
	
	public void setDescBreveMotivoAssenzaCig(String descBreveMotivoAssenzaCig) {
		this.descBreveMotivoAssenzaCig = descBreveMotivoAssenzaCig;
	}
	
	public String getDescMotivoAssenzaCig() {
		return descMotivoAssenzaCig;
	}
	
	public void setDescMotivoAssenzaCig(String descMotivoAssenzaCig) {
		this.descMotivoAssenzaCig = descMotivoAssenzaCig;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descBreveMotivoAssenzaCig != null && descMotivoAssenzaCig != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMotivoAssenzaCig != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoAssenzaCig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoAssenzaCig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveMotivoAssenzaCig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveMotivoAssenzaCig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMotivoAssenzaCig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMotivoAssenzaCig: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public String gettc22() {
		return tc22;
	}

	public void settc22(String tc22) {
		this.tc22 = tc22;
	}

	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idMotivoAssenzaCig");
		
	    return pk;
	}
	
	
}
