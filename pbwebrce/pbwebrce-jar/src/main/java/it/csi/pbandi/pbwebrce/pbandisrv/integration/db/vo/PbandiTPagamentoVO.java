/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTPagamentoVO extends GenericVO {

  	
  	private Date dtPagamento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtValuta;
  	
  	private BigDecimal idStatoValidazioneSpesaBck;
  	
  	private BigDecimal idModalitaPagamento;
  	
  	private BigDecimal importoPagamento;
  	
  	private BigDecimal idCaricaMassDocSpesa;
  	
  	private BigDecimal idErogazione;
  	
  	private BigDecimal idPagamento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSoggetto;
  	
	public PbandiTPagamentoVO() {}
  	
	public PbandiTPagamentoVO (BigDecimal idPagamento) {
	
		this. idPagamento =  idPagamento;
	}
  	
	public PbandiTPagamentoVO (Date dtPagamento, BigDecimal idUtenteAgg, Date dtValuta, BigDecimal idStatoValidazioneSpesaBck, BigDecimal idModalitaPagamento, BigDecimal importoPagamento, BigDecimal idCaricaMassDocSpesa, BigDecimal idErogazione, BigDecimal idPagamento, BigDecimal idUtenteIns, BigDecimal idSoggetto) {
	
		this. dtPagamento =  dtPagamento;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtValuta =  dtValuta;
		this. idStatoValidazioneSpesaBck =  idStatoValidazioneSpesaBck;
		this. idModalitaPagamento =  idModalitaPagamento;
		this. importoPagamento =  importoPagamento;
		this. idCaricaMassDocSpesa =  idCaricaMassDocSpesa;
		this. idErogazione =  idErogazione;
		this. idPagamento =  idPagamento;
		this. idUtenteIns =  idUtenteIns;
		this. idSoggetto =  idSoggetto;
	}
  	
  	
	public Date getDtPagamento() {
		return dtPagamento;
	}
	
	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtValuta() {
		return dtValuta;
	}
	
	public void setDtValuta(Date dtValuta) {
		this.dtValuta = dtValuta;
	}
	
	public BigDecimal getIdStatoValidazioneSpesaBck() {
		return idStatoValidazioneSpesaBck;
	}
	
	public void setIdStatoValidazioneSpesaBck(BigDecimal idStatoValidazioneSpesaBck) {
		this.idStatoValidazioneSpesaBck = idStatoValidazioneSpesaBck;
	}
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	
	public BigDecimal getIdCaricaMassDocSpesa() {
		return idCaricaMassDocSpesa;
	}
	
	public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
		this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
	}
	
	public BigDecimal getIdErogazione() {
		return idErogazione;
	}
	
	public void setIdErogazione(BigDecimal idErogazione) {
		this.idErogazione = idErogazione;
	}
	
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idModalitaPagamento != null && importoPagamento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPagamento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtValuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtValuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoValidazioneSpesaBck);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoValidazioneSpesaBck: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCaricaMassDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCaricaMassDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idPagamento");
		
	    return pk;
	}
	
	
}
