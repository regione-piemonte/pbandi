/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDNaturaCipeVO extends GenericVO {

  	
  	private String descNaturaCipe;
  	
  	private Date dtFineValidita;
  	
  	private String codNaturaCipe;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idNaturaCipe;
  	
  	private BigDecimal idTipoOperazione;
  	
	public PbandiDNaturaCipeVO() {}
  	
	public PbandiDNaturaCipeVO (BigDecimal idNaturaCipe) {
	
		this. idNaturaCipe =  idNaturaCipe;
	}
  	
	public PbandiDNaturaCipeVO (String descNaturaCipe, Date dtFineValidita, String codNaturaCipe, Date dtInizioValidita, BigDecimal idNaturaCipe, BigDecimal idTipoOperazione) {
	
		this.descNaturaCipe =  descNaturaCipe;
		this.dtFineValidita =  dtFineValidita;
		this.codNaturaCipe =  codNaturaCipe;
		this.dtInizioValidita =  dtInizioValidita;
		this.idNaturaCipe =  idNaturaCipe;
		this.idTipoOperazione =  idTipoOperazione;
	}
  	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

	public String getDescNaturaCipe() {
		return descNaturaCipe;
	}
	
	public void setDescNaturaCipe(String descNaturaCipe) {
		this.descNaturaCipe = descNaturaCipe;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodNaturaCipe() {
		return codNaturaCipe;
	}
	
	public void setCodNaturaCipe(String codNaturaCipe) {
		this.codNaturaCipe = codNaturaCipe;
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
                if (isPKValid() && descNaturaCipe != null && codNaturaCipe != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNaturaCipe != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNaturaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codNaturaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNaturaCipe");
		
	    return pk;
	}
	
	
}
