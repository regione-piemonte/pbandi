/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTSospensioneVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizio;
  	
  	private BigDecimal idSospensione;
  	
  	private String motivazione;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private Date dtFinePrevista;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtFine;
  	
	public PbandiTSospensioneVO() {}
  	
	public PbandiTSospensioneVO (BigDecimal idSospensione) {
	
		this. idSospensione =  idSospensione;
	}
  	
	public PbandiTSospensioneVO (BigDecimal idUtenteAgg, Date dtInizio, BigDecimal idSospensione, String motivazione, Date dtAggiornamento, Date dtInserimento, Date dtFinePrevista, BigDecimal idProgetto, BigDecimal idUtenteIns, Date dtFine) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizio =  dtInizio;
		this. idSospensione =  idSospensione;
		this. motivazione =  motivazione;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. dtFinePrevista =  dtFinePrevista;
		this. idProgetto =  idProgetto;
		this. idUtenteIns =  idUtenteIns;
		this. dtFine =  dtFine;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizio() {
		return dtInizio;
	}
	
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}
	
	public BigDecimal getIdSospensione() {
		return idSospensione;
	}
	
	public void setIdSospensione(BigDecimal idSospensione) {
		this.idSospensione = idSospensione;
	}
	
	public String getMotivazione() {
		return motivazione;
	}
	
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
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
	
	public Date getDtFinePrevista() {
		return dtFinePrevista;
	}
	
	public void setDtFinePrevista(Date dtFinePrevista) {
		this.dtFinePrevista = dtFinePrevista;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtFine() {
		return dtFine;
	}
	
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizio != null && motivazione != null && dtInserimento != null && dtFinePrevista != null && idProgetto != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSospensione != null ) {
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
	    
	    temp = DataFilter.removeNull( dtInizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSospensione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSospensione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( motivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" motivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFinePrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFinePrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFine);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFine: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSospensione");
		
	    return pk;
	}
	
	
}
