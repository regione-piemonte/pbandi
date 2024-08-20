/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDRegioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String codIstatRegione;
  	
  	private BigDecimal idRegione;
  	
  	private String descRegione;
  	
	public PbandiDRegioneVO() {}
  	
	public PbandiDRegioneVO (BigDecimal idRegione) {
	
		this. idRegione =  idRegione;
	}
  	
	public PbandiDRegioneVO (Date dtFineValidita, Date dtInizioValidita, String codIstatRegione, BigDecimal idRegione, String descRegione) {
	
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. codIstatRegione =  codIstatRegione;
		this. idRegione =  idRegione;
		this. descRegione =  descRegione;
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
	
	public String getCodIstatRegione() {
		return codIstatRegione;
	}
	
	public void setCodIstatRegione(String codIstatRegione) {
		this.codIstatRegione = codIstatRegione;
	}
	
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	
	public String getDescRegione() {
		return descRegione;
	}
	
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descRegione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRegione != null ) {
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
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIstatRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIstatRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descRegione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idRegione");
		
	    return pk;
	}
	
	
}
