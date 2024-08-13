/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoAccessoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descTipoAccesso;
  	
  	private Date dtInizioValidita;
  	
  	private String codTipoAccesso;
  	
  	private BigDecimal idTipoAccesso;
  	
	public PbandiDTipoAccessoVO() {}
  	
	public PbandiDTipoAccessoVO (BigDecimal idTipoAccesso) {
	
		this. idTipoAccesso =  idTipoAccesso;
	}
  	
	public PbandiDTipoAccessoVO (Date dtFineValidita, String descTipoAccesso, Date dtInizioValidita, String codTipoAccesso, BigDecimal idTipoAccesso) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descTipoAccesso =  descTipoAccesso;
		this. dtInizioValidita =  dtInizioValidita;
		this. codTipoAccesso =  codTipoAccesso;
		this. idTipoAccesso =  idTipoAccesso;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoAccesso() {
		return descTipoAccesso;
	}
	
	public void setDescTipoAccesso(String descTipoAccesso) {
		this.descTipoAccesso = descTipoAccesso;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodTipoAccesso() {
		return codTipoAccesso;
	}
	
	public void setCodTipoAccesso(String codTipoAccesso) {
		this.codTipoAccesso = codTipoAccesso;
	}
	
	public BigDecimal getIdTipoAccesso() {
		return idTipoAccesso;
	}
	
	public void setIdTipoAccesso(BigDecimal idTipoAccesso) {
		this.idTipoAccesso = idTipoAccesso;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoAccesso != null && dtInizioValidita != null && codTipoAccesso != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoAccesso != null ) {
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
	    
	    temp = DataFilter.removeNull( descTipoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codTipoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipoAccesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAccesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAccesso: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoAccesso");
		
	    return pk;
	}
	
	
}
