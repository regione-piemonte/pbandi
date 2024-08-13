/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLineaEnteCompVO extends GenericVO {

  	
  	private BigDecimal idRuoloEnteCompetenza;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal progrBandoLineaEnteComp;
  	
  	private String parolaChiave;
  	
  	private String feedbackActa;
  	
  	private String indirizzoMailPec;

	public String getIndirizzoMailPec() {
		return indirizzoMailPec;
	}

	public void setIndirizzoMailPec(String indirizzoMailPec) {
		this.indirizzoMailPec = indirizzoMailPec;
	}

	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}

	public String getFeedbackActa() {
		return feedbackActa;
	}

	public void setFeedbackActa(String feedbackActa) {
		this.feedbackActa = feedbackActa;
	}

	public PbandiRBandoLineaEnteCompVO() {}
  	
	public PbandiRBandoLineaEnteCompVO (BigDecimal progrBandoLineaEnteComp) {
	
		this. progrBandoLineaEnteComp =  progrBandoLineaEnteComp;
	}
  	
	public PbandiRBandoLineaEnteCompVO (BigDecimal idRuoloEnteCompetenza, BigDecimal progrBandoLineaIntervento, Date dtFineValidita, BigDecimal idEnteCompetenza, BigDecimal idUtenteAgg, BigDecimal idUtenteIns, BigDecimal progrBandoLineaEnteComp) {
	
		this. idRuoloEnteCompetenza =  idRuoloEnteCompetenza;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. dtFineValidita =  dtFineValidita;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
		this. progrBandoLineaEnteComp =  progrBandoLineaEnteComp;
	}
  	
  	
	public BigDecimal getIdRuoloEnteCompetenza() {
		return idRuoloEnteCompetenza;
	}
	
	public void setIdRuoloEnteCompetenza(BigDecimal idRuoloEnteCompetenza) {
		this.idRuoloEnteCompetenza = idRuoloEnteCompetenza;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getProgrBandoLineaEnteComp() {
		return progrBandoLineaEnteComp;
	}
	
	public void setProgrBandoLineaEnteComp(BigDecimal progrBandoLineaEnteComp) {
		this.progrBandoLineaEnteComp = progrBandoLineaEnteComp;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idRuoloEnteCompetenza != null && progrBandoLineaIntervento != null && idEnteCompetenza != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoLineaEnteComp != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idRuoloEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRuoloEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaEnteComp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaEnteComp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( parolaChiave);
	    if (!DataFilter.isEmpty(temp)) sb.append(" parolaChiave: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( feedbackActa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" feedbackActa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoLineaEnteComp");
		
	    return pk;
	}
	
	
}
