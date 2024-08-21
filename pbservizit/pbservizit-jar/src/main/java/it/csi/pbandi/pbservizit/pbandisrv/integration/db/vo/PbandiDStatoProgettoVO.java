/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoProgettoVO extends GenericVO {

  	
  	private String descStatoProgetto;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idStatoProgetto;
  	
  	private String descBreveStatoProgetto;
  	
  	private String flagCertificazione;

  	private String flagMonitoraggio;
  	
  	private String codFaseGefo;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoProgettoVO() {}
  	
	public PbandiDStatoProgettoVO (BigDecimal idStatoProgetto) {
	
		this. idStatoProgetto =  idStatoProgetto;
	}
  	
	public PbandiDStatoProgettoVO (String descStatoProgetto, Date dtFineValidita, BigDecimal idStatoProgetto, String descBreveStatoProgetto, String flagCertificazione, String codFaseGefo, Date dtInizioValidita) {
	
		this. descStatoProgetto =  descStatoProgetto;
		this. dtFineValidita =  dtFineValidita;
		this. idStatoProgetto =  idStatoProgetto;
		this. descBreveStatoProgetto =  descBreveStatoProgetto;
		this. flagCertificazione =  flagCertificazione;
		this. codFaseGefo =  codFaseGefo;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescStatoProgetto() {
		return descStatoProgetto;
	}
	
	public void setDescStatoProgetto(String descStatoProgetto) {
		this.descStatoProgetto = descStatoProgetto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdStatoProgetto() {
		return idStatoProgetto;
	}
	
	public void setIdStatoProgetto(BigDecimal idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}
	
	public String getDescBreveStatoProgetto() {
		return descBreveStatoProgetto;
	}
	
	public void setDescBreveStatoProgetto(String descBreveStatoProgetto) {
		this.descBreveStatoProgetto = descBreveStatoProgetto;
	}
	
	public String getFlagCertificazione() {
		return flagCertificazione;
	}
	
	public void setFlagCertificazione(String flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}
	
	public String getCodFaseGefo() {
		return codFaseGefo;
	}
	
	public void setCodFaseGefo(String codFaseGefo) {
		this.codFaseGefo = codFaseGefo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descStatoProgetto != null && descBreveStatoProgetto != null && flagCertificazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCertificazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCertificazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFaseGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFaseGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoProgetto");
		
	    return pk;
	}

	public void setFlagMonitoraggio(String flagMonitoraggio) {
		this.flagMonitoraggio = flagMonitoraggio;
	}

	public String getFlagMonitoraggio() {
		return flagMonitoraggio;
	}
	
	
}
