/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTDichiarazioneSpesaVO extends GenericVO {

  	
  	private Date periodoAl;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtDichiarazione;
  	
  	private String flagRichiestaIntegrativa;
  	
  	private String noteChiusuraValidazione;
  	
  	private BigDecimal idProgetto;
  	
  	private Date periodoDal;
  	
  	private BigDecimal idDichiarazioneSpesa;
  	
  	private Date dtChiusuraValidazione;
  	
  	private BigDecimal idTipoDichiarazSpesa;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String tipoInvioDs;
  	
  	private BigDecimal idDichiarazioneSpesaColl;
  	
  	private String rilievoContabile;
  	
  	private Date dtRilievoContabile;
  	
  	private Date dtChiusuraRilievo;
  	
  	private Date dtConfermaValidita;
  	
	public PbandiTDichiarazioneSpesaVO() {}
  	
	public PbandiTDichiarazioneSpesaVO (BigDecimal idDichiarazioneSpesa) {
	
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
	}
  	
	public PbandiTDichiarazioneSpesaVO (Date periodoAl, BigDecimal idUtenteAgg, Date dtDichiarazione, String flagRichiestaIntegrativa, String noteChiusuraValidazione, BigDecimal idProgetto, Date periodoDal, BigDecimal idDichiarazioneSpesa, Date dtChiusuraValidazione, BigDecimal idTipoDichiarazSpesa, BigDecimal idUtenteIns, String tipoInvioDs,BigDecimal idDichiarazioneSpesaColl) {
	
		this. periodoAl =  periodoAl;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtDichiarazione =  dtDichiarazione;
		this. flagRichiestaIntegrativa =  flagRichiestaIntegrativa;
		this. noteChiusuraValidazione =  noteChiusuraValidazione;
		this. idProgetto =  idProgetto;
		this. periodoDal =  periodoDal;
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. dtChiusuraValidazione =  dtChiusuraValidazione;
		this. idTipoDichiarazSpesa =  idTipoDichiarazSpesa;
		this. idUtenteIns =  idUtenteIns;
		this. tipoInvioDs = tipoInvioDs;
		this. idDichiarazioneSpesaColl = idDichiarazioneSpesaColl;
	}  		
  	
	public BigDecimal getIdDichiarazioneSpesaColl() {
		return idDichiarazioneSpesaColl;
	}

	public void setIdDichiarazioneSpesaColl(BigDecimal idDichiarazioneSpesaColl) {
		this.idDichiarazioneSpesaColl = idDichiarazioneSpesaColl;
	}

	public String getTipoInvioDs() {
		return tipoInvioDs;
	}

	public void setTipoInvioDs(String tipoInvioDs) {
		this.tipoInvioDs = tipoInvioDs;
	}

	public Date getPeriodoAl() {
		return periodoAl;
	}
	
	public void setPeriodoAl(Date periodoAl) {
		this.periodoAl = periodoAl;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtDichiarazione() {
		return dtDichiarazione;
	}
	
	public void setDtDichiarazione(Date dtDichiarazione) {
		this.dtDichiarazione = dtDichiarazione;
	}
	
	public String getFlagRichiestaIntegrativa() {
		return flagRichiestaIntegrativa;
	}
	
	public void setFlagRichiestaIntegrativa(String flagRichiestaIntegrativa) {
		this.flagRichiestaIntegrativa = flagRichiestaIntegrativa;
	}
	
	public String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}
	
	public void setNoteChiusuraValidazione(String noteChiusuraValidazione) {
		this.noteChiusuraValidazione = noteChiusuraValidazione;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getPeriodoDal() {
		return periodoDal;
	}
	
	public void setPeriodoDal(Date periodoDal) {
		this.periodoDal = periodoDal;
	}
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}
	
	public void setDtChiusuraValidazione(Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}
	
	public BigDecimal getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}
	
	public void setIdTipoDichiarazSpesa(BigDecimal idTipoDichiarazSpesa) {
		this.idTipoDichiarazSpesa = idTipoDichiarazSpesa;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public Date getDtChiusuraRilievo() {
		return dtChiusuraRilievo;
	}

	public void setDtChiusuraRilievo(Date dtChiusuraRilievo) {
		this.dtChiusuraRilievo = dtChiusuraRilievo;
	}

	public Date getDtConfermaValidita() {
		return dtConfermaValidita;
	}

	public void setDtConfermaValidita(Date dtConfermaValidita) {
		this.dtConfermaValidita = dtConfermaValidita;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && periodoAl != null && dtDichiarazione != null && flagRichiestaIntegrativa != null && idProgetto != null && periodoDal != null && idTipoDichiarazSpesa != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDichiarazioneSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( periodoAl);
	    if (!DataFilter.isEmpty(temp)) sb.append(" periodoAl: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtDichiarazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtDichiarazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRichiestaIntegrativa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRichiestaIntegrativa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteChiusuraValidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteChiusuraValidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( periodoDal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" periodoDal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtChiusuraValidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtChiusuraValidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDichiarazSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDichiarazSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoInvioDs);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoInvioDs: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesaColl);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesaColl: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( rilievoContabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" rilievoContabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRilievoContabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRilievoContabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtChiusuraRilievo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtChiusuraRilievo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtConfermaValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtConfermaValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDichiarazioneSpesa");
		
	    return pk;
	}
	
	
}
