/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTRecuperoVO extends GenericVO {

  	
  	private String estremiRecupero;
  	
  	private BigDecimal idTipoRecupero;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal importoRecupero;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private String note;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtRecupero;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal interessiRecupero;
  	
  	private String codRiferimentoRecupero;
  	
  	private BigDecimal idRecupero;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiTRecuperoVO() {}
  	
	public PbandiTRecuperoVO (BigDecimal idRecupero) {
	
		this. idRecupero =  idRecupero;
	}
  	
	public PbandiTRecuperoVO (String estremiRecupero, BigDecimal idTipoRecupero, BigDecimal idUtenteAgg, BigDecimal importoRecupero, Date dtAggiornamento, Date dtInserimento, String note, BigDecimal idModalitaAgevolazione, BigDecimal idProgetto, Date dtRecupero, BigDecimal idPeriodo, BigDecimal interessiRecupero, String codRiferimentoRecupero, BigDecimal idRecupero, BigDecimal idUtenteIns) {
	
		this. estremiRecupero =  estremiRecupero;
		this. idTipoRecupero =  idTipoRecupero;
		this. idUtenteAgg =  idUtenteAgg;
		this. importoRecupero =  importoRecupero;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. note =  note;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. idProgetto =  idProgetto;
		this. dtRecupero =  dtRecupero;
		this. idPeriodo =  idPeriodo;
		this. interessiRecupero =  interessiRecupero;
		this. codRiferimentoRecupero =  codRiferimentoRecupero;
		this. idRecupero =  idRecupero;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public String getEstremiRecupero() {
		return estremiRecupero;
	}
	
	public void setEstremiRecupero(String estremiRecupero) {
		this.estremiRecupero = estremiRecupero;
	}
	
	public BigDecimal getIdTipoRecupero() {
		return idTipoRecupero;
	}
	
	public void setIdTipoRecupero(BigDecimal idTipoRecupero) {
		this.idTipoRecupero = idTipoRecupero;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getImportoRecupero() {
		return importoRecupero;
	}
	
	public void setImportoRecupero(BigDecimal importoRecupero) {
		this.importoRecupero = importoRecupero;
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
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtRecupero() {
		return dtRecupero;
	}
	
	public void setDtRecupero(Date dtRecupero) {
		this.dtRecupero = dtRecupero;
	}
	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public BigDecimal getInteressiRecupero() {
		return interessiRecupero;
	}
	
	public void setInteressiRecupero(BigDecimal interessiRecupero) {
		this.interessiRecupero = interessiRecupero;
	}
	
	public String getCodRiferimentoRecupero() {
		return codRiferimentoRecupero;
	}
	
	public void setCodRiferimentoRecupero(String codRiferimentoRecupero) {
		this.codRiferimentoRecupero = codRiferimentoRecupero;
	}
	
	public BigDecimal getIdRecupero() {
		return idRecupero;
	}
	
	public void setIdRecupero(BigDecimal idRecupero) {
		this.idRecupero = idRecupero;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoRecupero != null && importoRecupero != null && dtInserimento != null && idProgetto != null && dtRecupero != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRecupero != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( estremiRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" estremiRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( interessiRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" interessiRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codRiferimentoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codRiferimentoRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRecupero");
		
	    return pk;
	}
	
	
}
