/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTRevocaVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idMotivoRevoca;
  	
  	private String estremi;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private String note;
  	
  	private BigDecimal interessiRevoca;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtRevoca;
  	
  	private BigDecimal idCausaleDisimpegno;
  	
  	private BigDecimal idRevoca;
  	
  	private BigDecimal idPeriodo;
  	
  	private BigDecimal importo;
  	
  	private BigDecimal idUtenteIns;
  	
  	//M.R.////////////
  	private String flagOrdineRecupero;
  	
  	private BigDecimal idMancatoRecupero;
  	/////////////////
  	
  	


	public PbandiTRevocaVO() {}
  	
	public PbandiTRevocaVO (BigDecimal idRevoca) {
	
		this. idRevoca =  idRevoca;
	}
  	
	public PbandiTRevocaVO (BigDecimal idUtenteAgg, BigDecimal idMotivoRevoca, String estremi, Date dtAggiornamento, Date dtInserimento, 
			BigDecimal idModalitaAgevolazione, String note, BigDecimal interessiRevoca, BigDecimal idProgetto, Date dtRevoca, BigDecimal idCausaleDisimpegno, 
			BigDecimal idRevoca, BigDecimal idPeriodo, BigDecimal importo, BigDecimal idUtenteIns, String flagOrdineRecupero, BigDecimal idMancatoRecupero) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. idMotivoRevoca =  idMotivoRevoca;
		this. estremi =  estremi;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idModalitaAgevolazione =  idModalitaAgevolazione;
		this. note =  note;
		this. interessiRevoca =  interessiRevoca;
		this. idProgetto =  idProgetto;
		this. dtRevoca =  dtRevoca;
		this. idCausaleDisimpegno =  idCausaleDisimpegno;
		this. idRevoca =  idRevoca;
		this. idPeriodo =  idPeriodo;
		this. importo =  importo;
		this. idUtenteIns =  idUtenteIns;
		this. flagOrdineRecupero = flagOrdineRecupero;
		this. idMancatoRecupero = idMancatoRecupero;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	
	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	
	public String getEstremi() {
		return estremi;
	}
	
	public void setEstremi(String estremi) {
		this.estremi = estremi;
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
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public BigDecimal getInteressiRevoca() {
		return interessiRevoca;
	}
	
	public void setInteressiRevoca(BigDecimal interessiRevoca) {
		this.interessiRevoca = interessiRevoca;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtRevoca() {
		return dtRevoca;
	}
	
	public void setDtRevoca(Date dtRevoca) {
		this.dtRevoca = dtRevoca;
	}
	
	public BigDecimal getIdCausaleDisimpegno() {
		return idCausaleDisimpegno;
	}
	
	public void setIdCausaleDisimpegno(BigDecimal idCausaleDisimpegno) {
		this.idCausaleDisimpegno = idCausaleDisimpegno;
	}
	
	public BigDecimal getIdRevoca() {
		return idRevoca;
	}
	
	public void setIdRevoca(BigDecimal idRevoca) {
		this.idRevoca = idRevoca;
	}
	
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagOrdineRecupero() {
		return flagOrdineRecupero;
	}

	public void setFlagOrdineRecupero(String flagOrdineRecupero) {
		this.flagOrdineRecupero = flagOrdineRecupero;
	}

	public BigDecimal getIdMancatoRecupero() {
		return idMancatoRecupero;
	}

	public void setIdMancatoRecupero(BigDecimal idMancatoRecupero) {
		this.idMancatoRecupero = idMancatoRecupero;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idModalitaAgevolazione != null && idProgetto != null && dtRevoca != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRevoca != null ) {
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
	    
	    temp = DataFilter.removeNull( idMotivoRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( estremi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" estremi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( interessiRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" interessiRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleDisimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleDisimpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPeriodo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPeriodo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagOrdineRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagOrdineRecupero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMancatoRecupero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMancatoRecupero: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRevoca");
		
	    return pk;
	}
	
	
}
