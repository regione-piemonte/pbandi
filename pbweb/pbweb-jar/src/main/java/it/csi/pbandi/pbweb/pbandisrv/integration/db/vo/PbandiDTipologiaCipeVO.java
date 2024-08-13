/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipologiaCipeVO extends GenericVO {

  	
  	private String descTipologiaCipe;
  	
  	private String codTipologiaCipe;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipologiaCipe;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idNaturaCipe;
  	
	public PbandiDTipologiaCipeVO() {}
  	
	public PbandiDTipologiaCipeVO (BigDecimal idTipologiaCipe) {
	
		this. idTipologiaCipe =  idTipologiaCipe;
	}
  	
	public PbandiDTipologiaCipeVO (String descTipologiaCipe, String codTipologiaCipe, Date dtFineValidita, BigDecimal idTipologiaCipe, Date dtInizioValidita, BigDecimal idNaturaCipe) {
	
		this. descTipologiaCipe =  descTipologiaCipe;
		this. codTipologiaCipe =  codTipologiaCipe;
		this. dtFineValidita =  dtFineValidita;
		this. idTipologiaCipe =  idTipologiaCipe;
		this. dtInizioValidita =  dtInizioValidita;
		this. idNaturaCipe =  idNaturaCipe;
	}
  	
  	
	public String getDescTipologiaCipe() {
		return descTipologiaCipe;
	}
	
	public void setDescTipologiaCipe(String descTipologiaCipe) {
		this.descTipologiaCipe = descTipologiaCipe;
	}
	
	public String getCodTipologiaCipe() {
		return codTipologiaCipe;
	}
	
	public void setCodTipologiaCipe(String codTipologiaCipe) {
		this.codTipologiaCipe = codTipologiaCipe;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipologiaCipe() {
		return idTipologiaCipe;
	}
	
	public void setIdTipologiaCipe(BigDecimal idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}
	
	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipologiaCipe != null && codTipologiaCipe != null && dtInizioValidita != null && idNaturaCipe != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaCipe != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologiaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipologiaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipologiaCipe");
		
	    return pk;
	}
	
	
}
