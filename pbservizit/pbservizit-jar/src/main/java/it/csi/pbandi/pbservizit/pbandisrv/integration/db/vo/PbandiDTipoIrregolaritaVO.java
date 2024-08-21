/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoIrregolaritaVO extends GenericVO {

  	
  	private String descTipoIrregolarita;
  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoIrregolarita;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoIrregolarita;
  	
	public PbandiDTipoIrregolaritaVO() {}
  	
	public PbandiDTipoIrregolaritaVO (BigDecimal idTipoIrregolarita) {
	
		this. idTipoIrregolarita =  idTipoIrregolarita;
	}
  	
	public PbandiDTipoIrregolaritaVO (String descTipoIrregolarita, Date dtFineValidita, String descBreveTipoIrregolarita, Date dtInizioValidita, BigDecimal idTipoIrregolarita) {
	
		this. descTipoIrregolarita =  descTipoIrregolarita;
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoIrregolarita =  descBreveTipoIrregolarita;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoIrregolarita =  idTipoIrregolarita;
	}
  	
  	
	public String getDescTipoIrregolarita() {
		return descTipoIrregolarita;
	}
	
	public void setDescTipoIrregolarita(String descTipoIrregolarita) {
		this.descTipoIrregolarita = descTipoIrregolarita;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoIrregolarita() {
		return descBreveTipoIrregolarita;
	}
	
	public void setDescBreveTipoIrregolarita(String descBreveTipoIrregolarita) {
		this.descBreveTipoIrregolarita = descBreveTipoIrregolarita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoIrregolarita() {
		return idTipoIrregolarita;
	}
	
	public void setIdTipoIrregolarita(BigDecimal idTipoIrregolarita) {
		this.idTipoIrregolarita = idTipoIrregolarita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoIrregolarita != null && descBreveTipoIrregolarita != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoIrregolarita != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoIrregolarita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoIrregolarita");
		
	    return pk;
	}
	
	
}
