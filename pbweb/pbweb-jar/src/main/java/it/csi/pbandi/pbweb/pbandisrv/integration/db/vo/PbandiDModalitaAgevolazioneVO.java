/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDModalitaAgevolazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private String descBreveModalitaAgevolaz;
  	
  	private String flagCertificazione;
  	
  	private String codIgrueT8;
  	
  	private String descModalitaAgevolazione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDModalitaAgevolazioneVO() {}
  	
	public PbandiDModalitaAgevolazioneVO (BigDecimal idModalitaAgevolazione) {
	
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
	}
  	
	public PbandiDModalitaAgevolazioneVO (Date dtFineValidita, BigDecimal idModalitaAgevolazione, String descBreveModalitaAgevolaz, String flagCertificazione, String codIgrueT8, String descModalitaAgevolazione, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. descBreveModalitaAgevolaz =  descBreveModalitaAgevolaz;
		this. flagCertificazione =  flagCertificazione;
		this. codIgrueT8 =  codIgrueT8;
		this. descModalitaAgevolazione =  descModalitaAgevolazione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	
	public String getFlagCertificazione() {
		return flagCertificazione;
	}
	
	public void setFlagCertificazione(String flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}
	
	public String getCodIgrueT8() {
		return codIgrueT8;
	}
	
	public void setCodIgrueT8(String codIgrueT8) {
		this.codIgrueT8 = codIgrueT8;
	}
	
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveModalitaAgevolaz != null && flagCertificazione != null && descModalitaAgevolazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idModalitaAgevolazione != null ) {
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
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveModalitaAgevolaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveModalitaAgevolaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCertificazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCertificazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT8);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT8: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idModalitaAgevolazione");
		
	    return pk;
	}
	
	
}
