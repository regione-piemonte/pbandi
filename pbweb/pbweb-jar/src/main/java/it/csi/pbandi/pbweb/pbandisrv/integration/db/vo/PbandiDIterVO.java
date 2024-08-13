/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDIterVO extends GenericVO {

  	
  	private BigDecimal idIter;
  	
  	private String descIter;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private Date dtFineValidita;
  	
  	private String codIgrueT35;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idNaturaCipe;
  	
	public PbandiDIterVO() {}
  	
	public PbandiDIterVO (BigDecimal idIter) {	
		this. idIter =  idIter;
	}
  	
	public PbandiDIterVO (BigDecimal idIter, String descIter, BigDecimal idTipoOperazione, Date dtFineValidita, String codIgrueT35, Date dtInizioValidita, BigDecimal idNaturaCipe) {	
		this.idIter =  idIter;
		this.descIter =  descIter;
		this.idTipoOperazione =  idTipoOperazione;
		this.dtFineValidita =  dtFineValidita;
		this.codIgrueT35 =  codIgrueT35;
		this.dtInizioValidita =  dtInizioValidita;
		this.idNaturaCipe = idNaturaCipe;
	}
  	
	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}

	public BigDecimal getIdIter() {
		return idIter;
	}
	
	public void setIdIter(BigDecimal idIter) {
		this.idIter = idIter;
	}
	
	public String getDescIter() {
		return descIter;
	}
	
	public void setDescIter(String descIter) {
		this.descIter = descIter;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodIgrueT35() {
		return codIgrueT35;
	}
	
	public void setCodIgrueT35(String codIgrueT35) {
		this.codIgrueT35 = codIgrueT35;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descIter != null && codIgrueT35 != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIter != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idIter);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIter: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descIter);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIter: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT35);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT35: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idIter");
	    return pk;
	}
	
}
