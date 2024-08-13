/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDCausaleErogazioneVO extends GenericVO {

  	
  	private String flagIterStandard;
  	
  	private String descBreveCausale;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private BigDecimal progrOrdinamento;
  	
  	private String descCausale;
  	
  	private BigDecimal idTipoTrattamento;
  	
  	private String flagCertificazione;
  	
  	private Date dtFineValidita;
  	
	public PbandiDCausaleErogazioneVO() {}
  	
	public PbandiDCausaleErogazioneVO (BigDecimal idCausaleErogazione) {
	
		this. idCausaleErogazione =  idCausaleErogazione;
	}
  	
	public PbandiDCausaleErogazioneVO (String flagIterStandard, String descBreveCausale, Date dtInizioValidita, BigDecimal idCausaleErogazione, BigDecimal progrOrdinamento, String descCausale, BigDecimal idTipoTrattamento, String flagCertificazione, Date dtFineValidita) {
	
		this. flagIterStandard =  flagIterStandard;
		this. descBreveCausale =  descBreveCausale;
		this. dtInizioValidita =  dtInizioValidita;
		this. idCausaleErogazione =  idCausaleErogazione;
		this. progrOrdinamento =  progrOrdinamento;
		this. descCausale =  descCausale;
		this. idTipoTrattamento =  idTipoTrattamento;
		this. flagCertificazione =  flagCertificazione;
		this. dtFineValidita =  dtFineValidita;
	}
  	
  	
	public String getFlagIterStandard() {
		return flagIterStandard;
	}
	
	public void setFlagIterStandard(String flagIterStandard) {
		this.flagIterStandard = flagIterStandard;
	}
	
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	
	public BigDecimal getProgrOrdinamento() {
		return progrOrdinamento;
	}
	
	public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
	}
	
	public String getDescCausale() {
		return descCausale;
	}
	
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	
	public BigDecimal getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	
	public void setIdTipoTrattamento(BigDecimal idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	
	public String getFlagCertificazione() {
		return flagCertificazione;
	}
	
	public void setFlagCertificazione(String flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagIterStandard != null && descBreveCausale != null && dtInizioValidita != null && progrOrdinamento != null && descCausale != null && flagCertificazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCausaleErogazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagIterStandard);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagIterStandard: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveCausale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveCausale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrOrdinamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrOrdinamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCausale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCausale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCertificazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCertificazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCausaleErogazione");
		
	    return pk;
	}
	
	
}
