/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRLiquidazioneVO extends GenericVO {

  	
  	private BigDecimal idAttoLiquidazione;
  	
  	private String flagRimossaBilancio;
  	
  	private BigDecimal annoEsercizio;
  	
  	private String cigLiquidazione;
  	
  	private BigDecimal progrLiquidazione;
  	
  	private BigDecimal numBilancioLiquidazione;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtInserimento;
  	
  	private String cupLiquidazione;
  	
  	private BigDecimal idImpegno;
  	
  	private BigDecimal progrLiquidazionePrecedente;
  	
  	private BigDecimal importoLiquidato;
  	
  	private Date dtAggBilancioLiquidazione;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idStatoLiquidazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtRifiutoRagioneria;
  	
  	private Date dtAggiornamento;
  	
	public PbandiRLiquidazioneVO() {}
  	
	public PbandiRLiquidazioneVO (BigDecimal progrLiquidazione) {
	
		this. progrLiquidazione =  progrLiquidazione;
	}
  	
	public PbandiRLiquidazioneVO (BigDecimal idAttoLiquidazione, String flagRimossaBilancio, BigDecimal annoEsercizio, String cigLiquidazione, BigDecimal progrLiquidazione, BigDecimal numBilancioLiquidazione, Date dtInizioValidita, BigDecimal idUtenteIns, Date dtInserimento, String cupLiquidazione, BigDecimal idImpegno, BigDecimal progrLiquidazionePrecedente, BigDecimal importoLiquidato, Date dtAggBilancioLiquidazione, Date dtFineValidita, BigDecimal idStatoLiquidazione, BigDecimal idUtenteAgg, Date dtRifiutoRagioneria, Date dtAggiornamento) {
	
		this. idAttoLiquidazione =  idAttoLiquidazione;
		this. flagRimossaBilancio =  flagRimossaBilancio;
		this. annoEsercizio =  annoEsercizio;
		this. cigLiquidazione =  cigLiquidazione;
		this. progrLiquidazione =  progrLiquidazione;
		this. numBilancioLiquidazione =  numBilancioLiquidazione;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteIns =  idUtenteIns;
		this. dtInserimento =  dtInserimento;
		this. cupLiquidazione =  cupLiquidazione;
		this. idImpegno =  idImpegno;
		this. progrLiquidazionePrecedente =  progrLiquidazionePrecedente;
		this. importoLiquidato =  importoLiquidato;
		this. dtAggBilancioLiquidazione =  dtAggBilancioLiquidazione;
		this. dtFineValidita =  dtFineValidita;
		this. idStatoLiquidazione =  idStatoLiquidazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtRifiutoRagioneria =  dtRifiutoRagioneria;
		this. dtAggiornamento =  dtAggiornamento;
	}
  	
  	
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	
	public String getFlagRimossaBilancio() {
		return flagRimossaBilancio;
	}
	
	public void setFlagRimossaBilancio(String flagRimossaBilancio) {
		this.flagRimossaBilancio = flagRimossaBilancio;
	}
	
	public BigDecimal getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(BigDecimal annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public String getCigLiquidazione() {
		return cigLiquidazione;
	}
	
	public void setCigLiquidazione(String cigLiquidazione) {
		this.cigLiquidazione = cigLiquidazione;
	}
	
	public BigDecimal getProgrLiquidazione() {
		return progrLiquidazione;
	}
	
	public void setProgrLiquidazione(BigDecimal progrLiquidazione) {
		this.progrLiquidazione = progrLiquidazione;
	}
	
	public BigDecimal getNumBilancioLiquidazione() {
		return numBilancioLiquidazione;
	}
	
	public void setNumBilancioLiquidazione(BigDecimal numBilancioLiquidazione) {
		this.numBilancioLiquidazione = numBilancioLiquidazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getCupLiquidazione() {
		return cupLiquidazione;
	}
	
	public void setCupLiquidazione(String cupLiquidazione) {
		this.cupLiquidazione = cupLiquidazione;
	}
	
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	
	public BigDecimal getProgrLiquidazionePrecedente() {
		return progrLiquidazionePrecedente;
	}
	
	public void setProgrLiquidazionePrecedente(BigDecimal progrLiquidazionePrecedente) {
		this.progrLiquidazionePrecedente = progrLiquidazionePrecedente;
	}
	
	public BigDecimal getImportoLiquidato() {
		return importoLiquidato;
	}
	
	public void setImportoLiquidato(BigDecimal importoLiquidato) {
		this.importoLiquidato = importoLiquidato;
	}
	
	public Date getDtAggBilancioLiquidazione() {
		return dtAggBilancioLiquidazione;
	}
	
	public void setDtAggBilancioLiquidazione(Date dtAggBilancioLiquidazione) {
		this.dtAggBilancioLiquidazione = dtAggBilancioLiquidazione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdStatoLiquidazione() {
		return idStatoLiquidazione;
	}
	
	public void setIdStatoLiquidazione(BigDecimal idStatoLiquidazione) {
		this.idStatoLiquidazione = idStatoLiquidazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtRifiutoRagioneria() {
		return dtRifiutoRagioneria;
	}
	
	public void setDtRifiutoRagioneria(Date dtRifiutoRagioneria) {
		this.dtRifiutoRagioneria = dtRifiutoRagioneria;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idAttoLiquidazione != null && flagRimossaBilancio != null && annoEsercizio != null && dtInizioValidita != null && idUtenteIns != null && dtInserimento != null && idImpegno != null && importoLiquidato != null && idStatoLiquidazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrLiquidazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRimossaBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRimossaBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoEsercizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoEsercizio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numBilancioLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numBilancioLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cupLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cupLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrLiquidazionePrecedente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrLiquidazionePrecedente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoLiquidato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoLiquidato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggBilancioLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggBilancioLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRifiutoRagioneria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRifiutoRagioneria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("progrLiquidazione");
		
	    return pk;
	}
	
	
}
