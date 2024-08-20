/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRProgSoggFinanziatVO extends GenericVO {

  	
  	private BigDecimal percQuotaSoggFinanziatore;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal progrProgSoggFinanziat;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idProvincia;
  	
  	private BigDecimal idDelibera;
  	
  	private String flagEconomie;
  	
  	private BigDecimal idSoggettoFinanziatore;
  	
  	private String note;
  	
  	private String estremiProvv;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idComune;
  	
  	private BigDecimal idNorma;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal impQuotaSoggFinanziatore;
  	
	public PbandiRProgSoggFinanziatVO() {}
  	
	public PbandiRProgSoggFinanziatVO (BigDecimal progrProgSoggFinanziat) {
	
		this. progrProgSoggFinanziat =  progrProgSoggFinanziat;
	}
  	
	public PbandiRProgSoggFinanziatVO (BigDecimal percQuotaSoggFinanziatore, BigDecimal idUtenteAgg, BigDecimal progrProgSoggFinanziat, Date dtAggiornamento, Date dtInserimento, BigDecimal idProvincia, BigDecimal idDelibera, String flagEconomie, BigDecimal idSoggettoFinanziatore, String note, String estremiProvv, BigDecimal idProgetto, BigDecimal idComune, BigDecimal idNorma, BigDecimal idPeriodo, BigDecimal idUtenteIns, BigDecimal idSoggetto) {
	
		this. percQuotaSoggFinanziatore =  percQuotaSoggFinanziatore;
		this. idUtenteAgg =  idUtenteAgg;
		this. progrProgSoggFinanziat =  progrProgSoggFinanziat;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idProvincia =  idProvincia;
		this. idDelibera =  idDelibera;
		this. flagEconomie =  flagEconomie;
		this. idSoggettoFinanziatore =  idSoggettoFinanziatore;
		this. note =  note;
		this. estremiProvv =  estremiProvv;
		this. idProgetto =  idProgetto;
		this. idComune =  idComune;
		this. idNorma =  idNorma;
		this. idPeriodo =  idPeriodo;
		this. idUtenteIns =  idUtenteIns;
		this. idSoggetto =  idSoggetto;
	}
  	
  	
	public BigDecimal getPercQuotaSoggFinanziatore() {
		return percQuotaSoggFinanziatore;
	}
	
	public void setPercQuotaSoggFinanziatore(BigDecimal percQuotaSoggFinanziatore) {
		this.percQuotaSoggFinanziatore = percQuotaSoggFinanziatore;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getProgrProgSoggFinanziat() {
		return progrProgSoggFinanziat;
	}
	
	public void setProgrProgSoggFinanziat(BigDecimal progrProgSoggFinanziat) {
		this.progrProgSoggFinanziat = progrProgSoggFinanziat;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	public BigDecimal getIdDelibera() {
		return idDelibera;
	}
	
	public void setIdDelibera(BigDecimal idDelibera) {
		this.idDelibera = idDelibera;
	}
	
	public String getFlagEconomie() {
		return flagEconomie;
	}
	
	public void setFlagEconomie(String flagEconomie) {
		this.flagEconomie = flagEconomie;
	}
	
	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}
	
	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getEstremiProvv() {
		return estremiProvv;
	}
	
	public void setEstremiProvv(String estremiProvv) {
		this.estremiProvv = estremiProvv;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdComune() {
		return idComune;
	}
	
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	
	public BigDecimal getIdNorma() {
		return idNorma;
	}
	
	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}
	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
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
	
	public void setImpQuotaSoggFinanziatore(BigDecimal impQuotaSoggFinanziatore) {
		this.impQuotaSoggFinanziatore = impQuotaSoggFinanziatore;
	}

	public BigDecimal getImpQuotaSoggFinanziatore() {
		return impQuotaSoggFinanziatore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && flagEconomie != null && idSoggettoFinanziatore != null && idProgetto != null && idPeriodo != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrProgSoggFinanziat != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( percQuotaSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percQuotaSoggFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrProgSoggFinanziat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrProgSoggFinanziat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDelibera);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDelibera: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEconomie);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEconomie: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoFinanziatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( estremiProvv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" estremiProvv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impQuotaSoggFinanziatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impQuotaSoggFinanziatore: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrProgSoggFinanziat");
		
	    return pk;
	}


	
	
}
