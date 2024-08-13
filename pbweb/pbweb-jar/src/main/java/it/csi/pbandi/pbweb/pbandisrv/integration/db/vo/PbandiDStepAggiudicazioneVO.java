/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStepAggiudicazioneVO extends GenericVO {

  	
  	private BigDecimal codIgrueT48;
  	
  	private Date dtFineValidita;
  	
  	private String descStep;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStepAggiudicazione;
  	
  	private BigDecimal idTipologiaAggiudicaz;
  	
	public PbandiDStepAggiudicazioneVO() {}
  	
	public PbandiDStepAggiudicazioneVO (BigDecimal idStepAggiudicazione) {
	
		this. idStepAggiudicazione =  idStepAggiudicazione;
	}
  	
	public PbandiDStepAggiudicazioneVO (BigDecimal codIgrueT48, Date dtFineValidita, String descStep, Date dtInizioValidita, BigDecimal idStepAggiudicazione, BigDecimal idTipologiaAggiudicaz) {
	
		this. codIgrueT48 =  codIgrueT48;
		this. dtFineValidita =  dtFineValidita;
		this. descStep =  descStep;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStepAggiudicazione =  idStepAggiudicazione;
		this. idTipologiaAggiudicaz =  idTipologiaAggiudicaz;
	}
  	
  	
	public BigDecimal getCodIgrueT48() {
		return codIgrueT48;
	}
	
	public void setCodIgrueT48(BigDecimal codIgrueT48) {
		this.codIgrueT48 = codIgrueT48;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStep() {
		return descStep;
	}
	
	public void setDescStep(String descStep) {
		this.descStep = descStep;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStepAggiudicazione() {
		return idStepAggiudicazione;
	}
	
	public void setIdStepAggiudicazione(BigDecimal idStepAggiudicazione) {
		this.idStepAggiudicazione = idStepAggiudicazione;
	}
	
	public BigDecimal getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	
	public void setIdTipologiaAggiudicaz(BigDecimal idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT48 != null && descStep != null && dtInizioValidita != null && idTipologiaAggiudicaz != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStepAggiudicazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT48);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT48: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStep);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStep: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStepAggiudicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStepAggiudicazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAggiudicaz: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStepAggiudicazione");
		
	    return pk;
	}
	
	
}
