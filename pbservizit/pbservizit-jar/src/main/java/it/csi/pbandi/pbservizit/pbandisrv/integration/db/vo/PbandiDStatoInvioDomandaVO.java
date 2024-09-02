/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoInvioDomandaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descStatoInvioDomanda;
  	
  	private BigDecimal idStatoInvioDomanda;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveStatoInvioDomanda;
  	
	public PbandiDStatoInvioDomandaVO() {}
  	
	public PbandiDStatoInvioDomandaVO (BigDecimal idStatoInvioDomanda) {
	
		this. idStatoInvioDomanda =  idStatoInvioDomanda;
	}
  	
	public PbandiDStatoInvioDomandaVO (Date dtFineValidita, String descStatoInvioDomanda, BigDecimal idStatoInvioDomanda, Date dtInizioValidita, String descBreveStatoInvioDomanda) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descStatoInvioDomanda =  descStatoInvioDomanda;
		this. idStatoInvioDomanda =  idStatoInvioDomanda;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveStatoInvioDomanda =  descBreveStatoInvioDomanda;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoInvioDomanda() {
		return descStatoInvioDomanda;
	}
	
	public void setDescStatoInvioDomanda(String descStatoInvioDomanda) {
		this.descStatoInvioDomanda = descStatoInvioDomanda;
	}
	
	public BigDecimal getIdStatoInvioDomanda() {
		return idStatoInvioDomanda;
	}
	
	public void setIdStatoInvioDomanda(BigDecimal idStatoInvioDomanda) {
		this.idStatoInvioDomanda = idStatoInvioDomanda;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveStatoInvioDomanda() {
		return descBreveStatoInvioDomanda;
	}
	
	public void setDescBreveStatoInvioDomanda(String descBreveStatoInvioDomanda) {
		this.descBreveStatoInvioDomanda = descBreveStatoInvioDomanda;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoInvioDomanda != null && dtInizioValidita != null && descBreveStatoInvioDomanda != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoInvioDomanda != null ) {
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
	    
	    temp = DataFilter.removeNull( descStatoInvioDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoInvioDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoInvioDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoInvioDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoInvioDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoInvioDomanda: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoInvioDomanda");
		
	    return pk;
	}
	
	
}