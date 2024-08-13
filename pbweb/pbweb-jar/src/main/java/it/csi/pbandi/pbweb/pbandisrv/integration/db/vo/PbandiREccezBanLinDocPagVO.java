/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiREccezBanLinDocPagVO extends GenericVO {

  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipoDocumentoSpesa;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal progrEccezBanLinDocPag;
  	
  	private BigDecimal idModalitaPagamento;
  	
  	private String flagAggiunta;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiREccezBanLinDocPagVO() {}
  	
	public PbandiREccezBanLinDocPagVO (BigDecimal progrEccezBanLinDocPag) {
	
		this. progrEccezBanLinDocPag =  progrEccezBanLinDocPag;
	}
  	
	public PbandiREccezBanLinDocPagVO (BigDecimal idUtenteAgg, BigDecimal idTipoDocumentoSpesa, Date dtAggiornamento, Date dtInserimento, BigDecimal progrEccezBanLinDocPag, BigDecimal idModalitaPagamento, String flagAggiunta, BigDecimal progrBandoLineaIntervento, BigDecimal idUtenteIns) {
	
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipoDocumentoSpesa =  idTipoDocumentoSpesa;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. progrEccezBanLinDocPag =  progrEccezBanLinDocPag;
		this. idModalitaPagamento =  idModalitaPagamento;
		this. flagAggiunta =  flagAggiunta;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
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
	
	public BigDecimal getProgrEccezBanLinDocPag() {
		return progrEccezBanLinDocPag;
	}
	
	public void setProgrEccezBanLinDocPag(BigDecimal progrEccezBanLinDocPag) {
		this.progrEccezBanLinDocPag = progrEccezBanLinDocPag;
	}
	
	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	
	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	
	public String getFlagAggiunta() {
		return flagAggiunta;
	}
	
	public void setFlagAggiunta(String flagAggiunta) {
		this.flagAggiunta = flagAggiunta;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idTipoDocumentoSpesa != null && dtInserimento != null && flagAggiunta != null && progrBandoLineaIntervento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrEccezBanLinDocPag != null ) {
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
	    
	    temp = DataFilter.removeNull( idTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrEccezBanLinDocPag);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrEccezBanLinDocPag: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaPagamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaPagamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAggiunta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAggiunta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrEccezBanLinDocPag");
		
	    return pk;
	}
	
	
}
