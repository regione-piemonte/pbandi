/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRSoggettiCorrelatiVO extends GenericVO {

  	
  	private String flagRichiestaContributo;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal quotaPartecipazione;
  	
  	private BigDecimal progrSoggettiCorrelati;
  	
  	private BigDecimal idTipoSoggettoCorrelato;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idSoggettoEnteGiuridico;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal idCarica;
  	
	public PbandiRSoggettiCorrelatiVO() {}
  	
	public PbandiRSoggettiCorrelatiVO (BigDecimal progrSoggettiCorrelati) {
	
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
	}
  	
	public PbandiRSoggettiCorrelatiVO (String flagRichiestaContributo, Date dtInizioValidita, BigDecimal idUtenteAgg, BigDecimal quotaPartecipazione, BigDecimal progrSoggettiCorrelati, BigDecimal idTipoSoggettoCorrelato, Date dtFineValidita, BigDecimal idSoggettoEnteGiuridico, BigDecimal idUtenteIns, BigDecimal idSoggetto, BigDecimal idCarica) {
	
		this. flagRichiestaContributo =  flagRichiestaContributo;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. quotaPartecipazione =  quotaPartecipazione;
		this. progrSoggettiCorrelati =  progrSoggettiCorrelati;
		this. idTipoSoggettoCorrelato =  idTipoSoggettoCorrelato;
		this. dtFineValidita =  dtFineValidita;
		this. idSoggettoEnteGiuridico =  idSoggettoEnteGiuridico;
		this. idUtenteIns =  idUtenteIns;
		this. idSoggetto =  idSoggetto;
		this. idCarica =  idCarica;
	}
  	
  	
	public String getFlagRichiestaContributo() {
		return flagRichiestaContributo;
	}
	
	public void setFlagRichiestaContributo(String flagRichiestaContributo) {
		this.flagRichiestaContributo = flagRichiestaContributo;
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
	
	public BigDecimal getQuotaPartecipazione() {
		return quotaPartecipazione;
	}
	
	public void setQuotaPartecipazione(BigDecimal quotaPartecipazione) {
		this.quotaPartecipazione = quotaPartecipazione;
	}
	
	public BigDecimal getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}
	
	public void setProgrSoggettiCorrelati(BigDecimal progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}
	
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdSoggettoEnteGiuridico() {
		return idSoggettoEnteGiuridico;
	}
	
	public void setIdSoggettoEnteGiuridico(BigDecimal idSoggettoEnteGiuridico) {
		this.idSoggettoEnteGiuridico = idSoggettoEnteGiuridico;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public BigDecimal getIdCarica() {
		return idCarica;
	}
	
	public void setIdCarica(BigDecimal idCarica) {
		this.idCarica = idCarica;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idTipoSoggettoCorrelato != null && idSoggettoEnteGiuridico != null && idUtenteIns != null && idSoggetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrSoggettiCorrelati != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRichiestaContributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRichiestaContributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quotaPartecipazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quotaPartecipazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrSoggettiCorrelati);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrSoggettiCorrelati: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggettoCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggettoCorrelato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCarica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCarica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrSoggettiCorrelati");
		
	    return pk;
	}
	
	
}
