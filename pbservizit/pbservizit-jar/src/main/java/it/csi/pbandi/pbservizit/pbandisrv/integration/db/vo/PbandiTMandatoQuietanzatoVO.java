/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTMandatoQuietanzatoVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal importoMandatoLordo;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal numeroMandato;
  	
  	private Date dtQuietanza;
  	
  	private String cupMandato;
  	
  	private String flagPignoramento;
  	
  	private BigDecimal importoMandatoNetto;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idMandatoQuietanzato;
  	
  	private String cigMandato;
  	
  	private BigDecimal importoRitenute;
  	
  	private BigDecimal progrLiquidazione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal importoQuietanzato;
  	
	public PbandiTMandatoQuietanzatoVO() {}
  	
	public PbandiTMandatoQuietanzatoVO (BigDecimal idMandatoQuietanzato) {
	
		this. idMandatoQuietanzato =  idMandatoQuietanzato;
	}
  	
	public PbandiTMandatoQuietanzatoVO (BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal importoMandatoLordo, Date dtAggiornamento, Date dtInserimento, BigDecimal numeroMandato, Date dtQuietanza, String cupMandato, String flagPignoramento, BigDecimal importoMandatoNetto, Date dtFineValidita, BigDecimal idMandatoQuietanzato, String cigMandato, BigDecimal importoRitenute, BigDecimal progrLiquidazione, BigDecimal idUtenteIns, BigDecimal importoQuietanzato) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. importoMandatoLordo =  importoMandatoLordo;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. numeroMandato =  numeroMandato;
		this. dtQuietanza =  dtQuietanza;
		this. cupMandato =  cupMandato;
		this. flagPignoramento =  flagPignoramento;
		this. importoMandatoNetto =  importoMandatoNetto;
		this. dtFineValidita =  dtFineValidita;
		this. idMandatoQuietanzato =  idMandatoQuietanzato;
		this. cigMandato =  cigMandato;
		this. importoRitenute =  importoRitenute;
		this. progrLiquidazione =  progrLiquidazione;
		this. idUtenteIns =  idUtenteIns;
		this. importoQuietanzato =  importoQuietanzato;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getImportoMandatoLordo() {
		return importoMandatoLordo;
	}
	
	public void setImportoMandatoLordo(BigDecimal importoMandatoLordo) {
		this.importoMandatoLordo = importoMandatoLordo;
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
	
	public BigDecimal getNumeroMandato() {
		return numeroMandato;
	}
	
	public void setNumeroMandato(BigDecimal numeroMandato) {
		this.numeroMandato = numeroMandato;
	}
	
	public Date getDtQuietanza() {
		return dtQuietanza;
	}
	
	public void setDtQuietanza(Date dtQuietanza) {
		this.dtQuietanza = dtQuietanza;
	}
	
	public String getCupMandato() {
		return cupMandato;
	}
	
	public void setCupMandato(String cupMandato) {
		this.cupMandato = cupMandato;
	}
	
	public String getFlagPignoramento() {
		return flagPignoramento;
	}
	
	public void setFlagPignoramento(String flagPignoramento) {
		this.flagPignoramento = flagPignoramento;
	}
	
	public BigDecimal getImportoMandatoNetto() {
		return importoMandatoNetto;
	}
	
	public void setImportoMandatoNetto(BigDecimal importoMandatoNetto) {
		this.importoMandatoNetto = importoMandatoNetto;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdMandatoQuietanzato() {
		return idMandatoQuietanzato;
	}
	
	public void setIdMandatoQuietanzato(BigDecimal idMandatoQuietanzato) {
		this.idMandatoQuietanzato = idMandatoQuietanzato;
	}
	
	public String getCigMandato() {
		return cigMandato;
	}
	
	public void setCigMandato(String cigMandato) {
		this.cigMandato = cigMandato;
	}
	
	public BigDecimal getImportoRitenute() {
		return importoRitenute;
	}
	
	public void setImportoRitenute(BigDecimal importoRitenute) {
		this.importoRitenute = importoRitenute;
	}
	
	public BigDecimal getProgrLiquidazione() {
		return progrLiquidazione;
	}
	
	public void setProgrLiquidazione(BigDecimal progrLiquidazione) {
		this.progrLiquidazione = progrLiquidazione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && dtInserimento != null && flagPignoramento != null && progrLiquidazione != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMandatoQuietanzato != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoMandatoLordo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoMandatoLordo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroMandato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroMandato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtQuietanza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtQuietanza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cupMandato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cupMandato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPignoramento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPignoramento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoMandatoNetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoMandatoNetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMandatoQuietanzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMandatoQuietanzato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigMandato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigMandato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRitenute);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRitenute: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoQuietanzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoQuietanzato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idMandatoQuietanzato");
		
	    return pk;
	}
	
	
}
