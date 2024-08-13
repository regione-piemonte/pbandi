/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDTipoProceduraOrigVO extends GenericVO {
  	  	
  	private BigDecimal idTipoProceduraOrig;
  	private String descTipoProceduraOrig;
  	private String descBreveTipoProceduraOrig;
  	private Date dtInizioValidita;
  	private Date dtFineValidita;
  	
	public PbandiDTipoProceduraOrigVO() {}
  	
	public PbandiDTipoProceduraOrigVO (BigDecimal idTipoProceduraOrig) {	
		this.idTipoProceduraOrig =  idTipoProceduraOrig;
	}
  	
	public PbandiDTipoProceduraOrigVO (BigDecimal idTipoProceduraOrig, String descTipoProceduraOrig, String descBreveTipoProceduraOrig, Date dtInizioValidita, Date dtFineValidita) {
		this.idTipoProceduraOrig = idTipoProceduraOrig;
		this.descTipoProceduraOrig = descTipoProceduraOrig;
		this.descBreveTipoProceduraOrig = descBreveTipoProceduraOrig;
		this.dtInizioValidita = dtInizioValidita;
		this.dtFineValidita = dtFineValidita;
	}

	public BigDecimal getIdTipoProceduraOrig() {
		return idTipoProceduraOrig;
	}

	public void setIdTipoProceduraOrig(BigDecimal idTipoProceduraOrig) {
		this.idTipoProceduraOrig = idTipoProceduraOrig;
	}

	public String getDescTipoProceduraOrig() {
		return descTipoProceduraOrig;
	}

	public void setDescTipoProceduraOrig(String descTipoProceduraOrig) {
		this.descTipoProceduraOrig = descTipoProceduraOrig;
	}

	public String getDescBreveTipoProceduraOrig() {
		return descBreveTipoProceduraOrig;
	}

	public void setDescBreveTipoProceduraOrig(String descBreveTipoProceduraOrig) {
		this.descBreveTipoProceduraOrig = descBreveTipoProceduraOrig;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoProceduraOrig != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("idTipoProceduraOrig");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoProceduraOrig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoProceduraOrig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoProceduraOrig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoProceduraOrig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoProceduraOrig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoProceduraOrig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}

}
