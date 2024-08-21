/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiRIterProcAggVO extends GenericVO {

  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoStep;
  	
  	private Date dtEffettiva;
  	
  	private Date dtPrevista;
  	
  	private BigDecimal idProceduraAggiudicaz;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idStepAggiudicazione;
  	
  	private BigDecimal idMotivoScostamento;
  	
	public PbandiRIterProcAggVO() {}
  	
	public PbandiRIterProcAggVO (BigDecimal idProceduraAggiudicaz, BigDecimal idStepAggiudicazione) {
	
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. idStepAggiudicazione =  idStepAggiudicazione;
	}
  	
	public PbandiRIterProcAggVO (Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal importoStep, Date dtEffettiva, Date dtPrevista, BigDecimal idProceduraAggiudicaz, Date dtFineValidita, BigDecimal idUtenteIns, BigDecimal idStepAggiudicazione, BigDecimal idMotivoScostamento) {
	
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoStep =  importoStep;
		this. dtEffettiva =  dtEffettiva;
		this. dtPrevista =  dtPrevista;
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. dtFineValidita =  dtFineValidita;
		this. idUtenteIns =  idUtenteIns;
		this. idStepAggiudicazione =  idStepAggiudicazione;
		this. idMotivoScostamento =  idMotivoScostamento;
	}
  	
  	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoStep() {
		return importoStep;
	}
	
	public void setImportoStep(BigDecimal importoStep) {
		this.importoStep = importoStep;
	}
	
	public Date getDtEffettiva() {
		return dtEffettiva;
	}
	
	public void setDtEffettiva(Date dtEffettiva) {
		this.dtEffettiva = dtEffettiva;
	}
	
	public Date getDtPrevista() {
		return dtPrevista;
	}
	
	public void setDtPrevista(Date dtPrevista) {
		this.dtPrevista = dtPrevista;
	}
	
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdStepAggiudicazione() {
		return idStepAggiudicazione;
	}
	
	public void setIdStepAggiudicazione(BigDecimal idStepAggiudicazione) {
		this.idStepAggiudicazione = idStepAggiudicazione;
	}
	
	public BigDecimal getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	
	public void setIdMotivoScostamento(BigDecimal idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && dtEffettiva != null && dtPrevista != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProceduraAggiudicaz != null && idStepAggiudicazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoStep);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoStep: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProceduraAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProceduraAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStepAggiudicazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStepAggiudicazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoScostamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProceduraAggiudicaz");
		
			pk.add("idStepAggiudicazione");
		
	    return pk;
	}
	
	
}
