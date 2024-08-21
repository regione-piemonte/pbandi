/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoConfrontoVO extends GenericVO {

  	
  	private String descBreveTipoConfronto;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoConfronto;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoConfronto;
  	
	public PbandiDTipoConfrontoVO() {}
  	
	public PbandiDTipoConfrontoVO (BigDecimal idTipoConfronto) {
	
		this. idTipoConfronto =  idTipoConfronto;
	}
  	
	public PbandiDTipoConfrontoVO (String descBreveTipoConfronto, Date dtFineValidita, String descTipoConfronto, Date dtInizioValidita, BigDecimal idTipoConfronto) {
	
		this. descBreveTipoConfronto =  descBreveTipoConfronto;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoConfronto =  descTipoConfronto;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoConfronto =  idTipoConfronto;
	}
  	
  	
	public String getDescBreveTipoConfronto() {
		return descBreveTipoConfronto;
	}
	
	public void setDescBreveTipoConfronto(String descBreveTipoConfronto) {
		this.descBreveTipoConfronto = descBreveTipoConfronto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoConfronto() {
		return descTipoConfronto;
	}
	
	public void setDescTipoConfronto(String descTipoConfronto) {
		this.descTipoConfronto = descTipoConfronto;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoConfronto() {
		return idTipoConfronto;
	}
	
	public void setIdTipoConfronto(BigDecimal idTipoConfronto) {
		this.idTipoConfronto = idTipoConfronto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoConfronto != null && descTipoConfronto != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoConfronto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoConfronto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoConfronto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoConfronto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoConfronto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoConfronto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoConfronto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoConfronto");
		
	    return pk;
	}
	
	
}
