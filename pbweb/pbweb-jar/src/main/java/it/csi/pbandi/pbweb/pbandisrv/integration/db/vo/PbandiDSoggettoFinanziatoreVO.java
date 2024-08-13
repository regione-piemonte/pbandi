/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSoggettoFinanziatoreVO extends GenericVO {

  	
  	private String flagAgevolato;
  	
  	private String descSoggFinanziatore;
  	
  	private BigDecimal codIgrueT25;
  	
  	private String descBreveSoggFinanziatore;
  	
  	private Date dtInizioValidita;
  	
  	private String codCipeCup;
  	
  	private BigDecimal idSoggettoFinanziatore;
  	
  	private BigDecimal idTipoFondo;
  	
  	private BigDecimal idTipoSoggFinanziat;
  	
  	private String flagCertificazione;
  	
  	private Date dtFineValidita;
  	
	public PbandiDSoggettoFinanziatoreVO() {}
  	
	public PbandiDSoggettoFinanziatoreVO (BigDecimal idSoggettoFinanziatore) {
	
		this. idSoggettoFinanziatore =  idSoggettoFinanziatore;
	}
  	
	public PbandiDSoggettoFinanziatoreVO (String flagAgevolato, String descSoggFinanziatore, BigDecimal codIgrueT25, String descBreveSoggFinanziatore, Date dtInizioValidita, String codCipeCup, BigDecimal idSoggettoFinanziatore, BigDecimal idTipoFondo, BigDecimal idTipoSoggFinanziat, String flagCertificazione, Date dtFineValidita) {
	
		this. flagAgevolato =  flagAgevolato;
		this. descSoggFinanziatore =  descSoggFinanziatore;
		this. codIgrueT25 =  codIgrueT25;
		this. descBreveSoggFinanziatore =  descBreveSoggFinanziatore;
		this. dtInizioValidita =  dtInizioValidita;
		this. codCipeCup =  codCipeCup;
		this. idSoggettoFinanziatore =  idSoggettoFinanziatore;
		this. idTipoFondo =  idTipoFondo;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
		this. flagCertificazione =  flagCertificazione;
		this. dtFineValidita =  dtFineValidita;
	}
  	
  	
	public String getFlagAgevolato() {
		return flagAgevolato;
	}
	
	public void setFlagAgevolato(String flagAgevolato) {
		this.flagAgevolato = flagAgevolato;
	}
	
	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}
	
	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}
	
	public BigDecimal getCodIgrueT25() {
		return codIgrueT25;
	}
	
	public void setCodIgrueT25(BigDecimal codIgrueT25) {
		this.codIgrueT25 = codIgrueT25;
	}
	
	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}
	
	public void setDescBreveSoggFinanziatore(String descBreveSoggFinanziatore) {
		this.descBreveSoggFinanziatore = descBreveSoggFinanziatore;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodCipeCup() {
		return codCipeCup;
	}
	
	public void setCodCipeCup(String codCipeCup) {
		this.codCipeCup = codCipeCup;
	}
	
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}
	
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
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
                if (isPKValid() && flagAgevolato != null && descSoggFinanziatore != null && descBreveSoggFinanziatore != null && dtInizioValidita != null && idTipoSoggFinanziat != null && flagCertificazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSoggettoFinanziatore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT25);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT25: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codCipeCup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codCipeCup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggFinanziat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCertificazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCertificazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idSoggettoFinanziatore");
		
	    return pk;
	}
	
	
}
