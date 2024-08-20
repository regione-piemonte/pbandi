/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTNotificaProcessoVO extends GenericVO {

  	private BigDecimal idNotifica;  	
  	private BigDecimal idProgetto;
  	private BigDecimal idRuoloDiProcessoDest;
  	private String subjectNotifica;
  	private String messageNotifica;
  	private String statoNotifica;
  	private BigDecimal idUtenteMitt;
  	private Date dtNotifica;
  	private BigDecimal idUtenteAgg;
  	private Date dtAggStatoNotifica;
  	private BigDecimal idTemplateNotifica;
  	private BigDecimal idEntita;
  	private BigDecimal idTarget;
  	
	public PbandiTNotificaProcessoVO() {}
  	
	public PbandiTNotificaProcessoVO (BigDecimal idNotifica) {	
		this.idNotifica =  idNotifica;
	}
  	
	public PbandiTNotificaProcessoVO (BigDecimal idNotifica, BigDecimal idProgetto, BigDecimal idRuoloDiProcessoDest, String subjectNotifica, String messageNotifica, String statoNotifica, BigDecimal idUtenteMitt, Date dtNotifica, BigDecimal idUtenteAgg, Date dtAggStatoNotifica, BigDecimal idTemplateNotifica, BigDecimal idEntita, BigDecimal idTarget) {
		this.idNotifica =  idNotifica;
		this.idProgetto =  idProgetto;
		this.idRuoloDiProcessoDest =  idRuoloDiProcessoDest;
		this.subjectNotifica =  subjectNotifica;
		this.messageNotifica =  messageNotifica;
		this.statoNotifica =  statoNotifica;
		this.idUtenteMitt =  idUtenteMitt;
		this.dtNotifica =  dtNotifica;
		this.idUtenteAgg =  idUtenteAgg;
		this.dtAggStatoNotifica =  dtAggStatoNotifica;
		this.idTemplateNotifica =  idTemplateNotifica;
		this.idEntita =  idEntita;
		this.idTarget =  idTarget;
	}
  	

	
	public BigDecimal getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(BigDecimal idNotifica) {
		this.idNotifica = idNotifica;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdRuoloDiProcessoDest() {
		return idRuoloDiProcessoDest;
	}

	public void setIdRuoloDiProcessoDest(BigDecimal idRuoloDiProcessoDest) {
		this.idRuoloDiProcessoDest = idRuoloDiProcessoDest;
	}

	public String getSubjectNotifica() {
		return subjectNotifica;
	}

	public void setSubjectNotifica(String subjectNotifica) {
		this.subjectNotifica = subjectNotifica;
	}

	public String getMessageNotifica() {
		return messageNotifica;
	}

	public void setMessageNotifica(String messageNotifica) {
		this.messageNotifica = messageNotifica;
	}

	public String getStatoNotifica() {
		return statoNotifica;
	}

	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}

	public BigDecimal getIdUtenteMitt() {
		return idUtenteMitt;
	}

	public void setIdUtenteMitt(BigDecimal idUtenteMitt) {
		this.idUtenteMitt = idUtenteMitt;
	}

	public Date getDtNotifica() {
		return dtNotifica;
	}

	public void setDtNotifica(Date dtNotifica) {
		this.dtNotifica = dtNotifica;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public Date getDtAggStatoNotifica() {
		return dtAggStatoNotifica;
	}

	public void setDtAggStatoNotifica(Date dtAggStatoNotifica) {
		this.dtAggStatoNotifica = dtAggStatoNotifica;
	}

	public BigDecimal getIdTemplateNotifica() {
		return idTemplateNotifica;
	}

	public void setIdTemplateNotifica(BigDecimal idTemplateNotifica) {
		this.idTemplateNotifica = idTemplateNotifica;
	}

	public BigDecimal getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}

	public BigDecimal getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNotifica != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();	
			pk.add("idNotifica");		
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTarget);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTarget: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( subjectNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" subjectNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( messageNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" messageNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtNotifica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
}
