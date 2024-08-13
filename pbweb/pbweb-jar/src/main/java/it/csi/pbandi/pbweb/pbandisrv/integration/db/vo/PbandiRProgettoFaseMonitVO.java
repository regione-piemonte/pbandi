/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRProgettoFaseMonitVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private Date dtFinePrevista;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtInizioEffettiva;
  	
  	private BigDecimal idFaseMonit;
  	
  	private Date dtFineEffettiva;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idMotivoScostamento;
  	
  	private Date dtInizioPrevista;
  	
	public PbandiRProgettoFaseMonitVO() {}
  	
	public PbandiRProgettoFaseMonitVO (BigDecimal idProgetto, BigDecimal idFaseMonit) {
	
		this. idProgetto =  idProgetto;
		this. idFaseMonit =  idFaseMonit;
	}
  	
	public PbandiRProgettoFaseMonitVO (BigDecimal idUtenteAgg, Date dtAggiornamento, Date dtInserimento, Date dtFinePrevista, BigDecimal idProgetto, Date dtInizioEffettiva, BigDecimal idFaseMonit, Date dtFineEffettiva, BigDecimal idUtenteIns, BigDecimal idMotivoScostamento, Date dtInizioPrevista) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. dtFinePrevista =  dtFinePrevista;
		this. idProgetto =  idProgetto;
		this. dtInizioEffettiva =  dtInizioEffettiva;
		this. idFaseMonit =  idFaseMonit;
		this. dtFineEffettiva =  dtFineEffettiva;
		this. idUtenteIns =  idUtenteIns;
		this. idMotivoScostamento =  idMotivoScostamento;
		this. dtInizioPrevista =  dtInizioPrevista;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
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
	
	public Date getDtInizioEffettiva() {
		return dtInizioEffettiva;
	}
	
	public void setDtInizioEffettiva(Date dtInizioEffettiva) {
		this.dtInizioEffettiva = dtInizioEffettiva;
	}
	
	public BigDecimal getIdFaseMonit() {
		return idFaseMonit;
	}
	
	public void setIdFaseMonit(BigDecimal idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}
	
	public Date getDtFineEffettiva() {
		return dtFineEffettiva;
	}
	
	public void setDtFineEffettiva(Date dtFineEffettiva) {
		this.dtFineEffettiva = dtFineEffettiva;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	
	public void setIdMotivoScostamento(BigDecimal idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	
	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}
	
	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgetto != null && idFaseMonit != null ) {
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
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFinePrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFinePrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineEffettiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineEffettiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoScostamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoScostamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioPrevista: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProgetto");
		
			pk.add("idFaseMonit");
		
	    return pk;
	}
	
	
}
