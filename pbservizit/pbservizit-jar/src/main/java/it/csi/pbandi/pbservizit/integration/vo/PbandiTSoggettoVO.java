/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiTSoggettoVO  {

	private BigDecimal idTipoSoggetto;
  	private BigDecimal idUtenteAgg;
  	private String codiceFiscaleSoggetto;
  	private BigDecimal idSoggetto;
  	private Date dtAggiornamento;
  	private Date dtInserimento;
  	private BigDecimal idUtenteIns;
  	
  	
	public PbandiTSoggettoVO() {}
  	
	public PbandiTSoggettoVO (BigDecimal idSoggetto) {
		this. idSoggetto =  idSoggetto;
	}
  	
	public PbandiTSoggettoVO (BigDecimal idTipoSoggetto, BigDecimal idUtenteAgg, String codiceFiscaleSoggetto, BigDecimal idSoggetto, Date dtAggiornamento, Date dtInserimento, BigDecimal idUtenteIns) {
		this. idTipoSoggetto =  idTipoSoggetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. codiceFiscaleSoggetto =  codiceFiscaleSoggetto;
		this. idSoggetto =  idSoggetto;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
	}
  	
  	
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
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
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
//	public boolean isValid() {
//		boolean isValid = false;
//                if (isPKValid() && idTipoSoggetto != null && codiceFiscaleSoggetto != null && dtInserimento != null && idUtenteIns != null) {
//   			isValid = true;
//   		}
//   		return isValid;
//	}
//	public boolean isPKValid() {
//		boolean isPkValid = false;
//		if (idSoggetto != null ) {
//   			isPkValid = true;
//   		}
//
//   		return isPkValid;
//	}
	
	
	@Override
	public String toString() {
	    StringBuffer sb = new StringBuffer();
	    sb.append("PbandiTSoggettoVO [idTipoSoggetto=" + idTipoSoggetto + ", idUtenteAgg=" + idUtenteAgg);
	    sb.append(", codiceFiscaleSoggetto=" + codiceFiscaleSoggetto + ", idSoggetto=" + idSoggetto);
	    sb.append(", dtAggiornamento=" + dtAggiornamento + ", dtInserimento=" + dtInserimento );
	    sb.append(", idUtenteIns=" + idUtenteIns + "]");;
	    return sb.toString();
	}
	
//	public java.util.List<String> getPK() {
//		java.util.List<String> pk=new java.util.ArrayList<String>();
//		
//			pk.add("idSoggetto");
//		
//	    return pk;
//	}
}
