/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoContoEconomicoVO extends GenericVO {

  	
  	private BigDecimal idTipologiaContoEconomico;
  	
  	private Date dtFineValidita;
  	
  	private String descStatoContoEconomico;
  	
  	private String descBreveStatoContoEconom;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStatoContoEconomico;
  	
	public PbandiDStatoContoEconomicoVO() {}
  	
	public PbandiDStatoContoEconomicoVO (BigDecimal idStatoContoEconomico) {
	
		this. idStatoContoEconomico =  idStatoContoEconomico;
	}
  	
	public PbandiDStatoContoEconomicoVO (BigDecimal idTipologiaContoEconomico, Date dtFineValidita, String descStatoContoEconomico, String descBreveStatoContoEconom, Date dtInizioValidita, BigDecimal idStatoContoEconomico) {
	
		this. idTipologiaContoEconomico =  idTipologiaContoEconomico;
		this. dtFineValidita =  dtFineValidita;
		this. descStatoContoEconomico =  descStatoContoEconomico;
		this. descBreveStatoContoEconom =  descBreveStatoContoEconom;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStatoContoEconomico =  idStatoContoEconomico;
	}
  	
  	
	public BigDecimal getIdTipologiaContoEconomico() {
		return idTipologiaContoEconomico;
	}
	
	public void setIdTipologiaContoEconomico(BigDecimal idTipologiaContoEconomico) {
		this.idTipologiaContoEconomico = idTipologiaContoEconomico;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoContoEconomico() {
		return descStatoContoEconomico;
	}
	
	public void setDescStatoContoEconomico(String descStatoContoEconomico) {
		this.descStatoContoEconomico = descStatoContoEconomico;
	}
	
	public String getDescBreveStatoContoEconom() {
		return descBreveStatoContoEconom;
	}
	
	public void setDescBreveStatoContoEconom(String descBreveStatoContoEconom) {
		this.descBreveStatoContoEconom = descBreveStatoContoEconom;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStatoContoEconomico() {
		return idStatoContoEconomico;
	}
	
	public void setIdStatoContoEconomico(BigDecimal idStatoContoEconomico) {
		this.idStatoContoEconomico = idStatoContoEconomico;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoContoEconomico != null && descBreveStatoContoEconom != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoContoEconomico != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoContoEconom);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoContoEconom: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoContoEconomico: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoContoEconomico");
		
	    return pk;
	}
	
	
}
