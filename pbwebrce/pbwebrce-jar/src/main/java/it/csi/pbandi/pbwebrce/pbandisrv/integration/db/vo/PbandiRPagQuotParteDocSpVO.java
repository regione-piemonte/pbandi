/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiRPagQuotParteDocSpVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idDichiarazioneSpesa;
  	
  	private BigDecimal idQuotaParteDocSpesa;
  	
  	private BigDecimal idPagamento;
  	
  	private BigDecimal importoValidato;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal progrPagQuotParteDocSp;
  	
  	private BigDecimal importoQuietanzato;
  	
	public PbandiRPagQuotParteDocSpVO() {}
  	
	public PbandiRPagQuotParteDocSpVO (BigDecimal progrPagQuotParteDocSp) {
	
		this. progrPagQuotParteDocSp =  progrPagQuotParteDocSp;
	}
  	
	public PbandiRPagQuotParteDocSpVO (BigDecimal idUtenteAgg, Date dtAggiornamento, Date dtInserimento, BigDecimal idDichiarazioneSpesa, BigDecimal idQuotaParteDocSpesa, BigDecimal idPagamento, BigDecimal importoValidato, BigDecimal idUtenteIns, BigDecimal progrPagQuotParteDocSp, BigDecimal importoQuietanzato) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. idQuotaParteDocSpesa =  idQuotaParteDocSpesa;
		this. idPagamento =  idPagamento;
		this. importoValidato =  importoValidato;
		this. idUtenteIns =  idUtenteIns;
		this. progrPagQuotParteDocSp =  progrPagQuotParteDocSp;
		this. importoQuietanzato =  importoQuietanzato;
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
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && idQuotaParteDocSpesa != null && idPagamento != null && idUtenteIns != null && importoQuietanzato != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrPagQuotParteDocSp != null ) {
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
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idQuotaParteDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQuotaParteDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoValidato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoValidato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrPagQuotParteDocSp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrPagQuotParteDocSp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoQuietanzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoQuietanzato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("progrPagQuotParteDocSp");
		
	    return pk;
	}
	
	
}
