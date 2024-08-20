/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAltraCassaPrevVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoAltraCassa;
  	
  	private BigDecimal idTipoAltraCassaPrev;
  	
  	private String descTipoAltraCassa;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoAltraCassaPrevVO() {}
  	
	public PbandiDTipoAltraCassaPrevVO (BigDecimal idTipoAltraCassaPrev) {
	
		this. idTipoAltraCassaPrev =  idTipoAltraCassaPrev;
	}
  	
	public PbandiDTipoAltraCassaPrevVO (Date dtFineValidita, String descBreveTipoAltraCassa, BigDecimal idTipoAltraCassaPrev, String descTipoAltraCassa, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoAltraCassa =  descBreveTipoAltraCassa;
		this. idTipoAltraCassaPrev =  idTipoAltraCassaPrev;
		this. descTipoAltraCassa =  descTipoAltraCassa;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoAltraCassa() {
		return descBreveTipoAltraCassa;
	}
	
	public void setDescBreveTipoAltraCassa(String descBreveTipoAltraCassa) {
		this.descBreveTipoAltraCassa = descBreveTipoAltraCassa;
	}
	
	public BigDecimal getIdTipoAltraCassaPrev() {
		return idTipoAltraCassaPrev;
	}
	
	public void setIdTipoAltraCassaPrev(BigDecimal idTipoAltraCassaPrev) {
		this.idTipoAltraCassaPrev = idTipoAltraCassaPrev;
	}
	
	public String getDescTipoAltraCassa() {
		return descTipoAltraCassa;
	}
	
	public void setDescTipoAltraCassa(String descTipoAltraCassa) {
		this.descTipoAltraCassa = descTipoAltraCassa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoAltraCassa != null && descTipoAltraCassa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAltraCassaPrev != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoAltraCassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoAltraCassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAltraCassaPrev);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAltraCassaPrev: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoAltraCassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAltraCassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoAltraCassaPrev");
		
	    return pk;
	}
	
	
}
