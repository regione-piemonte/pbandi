/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTSalVO extends GenericVO {

  	
  	private String descSal;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal importoSal;
  	
  	private BigDecimal idProceduraAggiudicaz;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtSal;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSal;
  	
	public PbandiTSalVO() {}
  	
	public PbandiTSalVO (BigDecimal idSal) {
	
		this. idSal =  idSal;
	}
  	
	public PbandiTSalVO (String descSal, BigDecimal idUtenteAgg, Date dtAggiornamento, Date dtInserimento, BigDecimal importoSal, BigDecimal idProceduraAggiudicaz, BigDecimal idProgetto, Date dtSal, BigDecimal idUtenteIns, BigDecimal idSal) {
	
		this. descSal =  descSal;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. importoSal =  importoSal;
		this. idProceduraAggiudicaz =  idProceduraAggiudicaz;
		this. idProgetto =  idProgetto;
		this. dtSal =  dtSal;
		this. idUtenteIns =  idUtenteIns;
		this. idSal =  idSal;
	}
  	
  	
	public String getDescSal() {
		return descSal;
	}
	
	public void setDescSal(String descSal) {
		this.descSal = descSal;
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
	
	public BigDecimal getImportoSal() {
		return importoSal;
	}
	
	public void setImportoSal(BigDecimal importoSal) {
		this.importoSal = importoSal;
	}
	
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtSal() {
		return dtSal;
	}
	
	public void setDtSal(Date dtSal) {
		this.dtSal = dtSal;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSal() {
		return idSal;
	}
	
	public void setIdSal(BigDecimal idSal) {
		this.idSal = idSal;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descSal != null && dtInserimento != null && importoSal != null && idProgetto != null && dtSal != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSal != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descSal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoSal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProceduraAggiudicaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProceduraAggiudicaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtSal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtSal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSal: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSal");
		
	    return pk;
	}
	
	
}
