/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PbandiLProcessoBatchVO {
	private Date dtFineElaborazione;
  	
  	private BigDecimal idProcessoBatch;
  	
  	private String flagEsito;
  	
  	private BigDecimal idNomeBatch;
  	
  	private Date dtInizioElaborazione;
  	
	public PbandiLProcessoBatchVO() {}
  	
	public PbandiLProcessoBatchVO (BigDecimal idProcessoBatch) {
	
		this. idProcessoBatch =  idProcessoBatch;
	}
  	
	public PbandiLProcessoBatchVO (Date dtFineElaborazione, BigDecimal idProcessoBatch, String flagEsito, BigDecimal idNomeBatch, Date dtInizioElaborazione) {
	
		this. dtFineElaborazione =  dtFineElaborazione;
		this. idProcessoBatch =  idProcessoBatch;
		this. flagEsito =  flagEsito;
		this. idNomeBatch =  idNomeBatch;
		this. dtInizioElaborazione =  dtInizioElaborazione;
	}
  	
  	
	public Date getDtFineElaborazione() {
		return dtFineElaborazione;
	}
	
	public void setDtFineElaborazione(Date dtFineElaborazione) {
		this.dtFineElaborazione = dtFineElaborazione;
	}
	
	public BigDecimal getIdProcessoBatch() {
		return idProcessoBatch;
	}
	
	public void setIdProcessoBatch(BigDecimal idProcessoBatch) {
		this.idProcessoBatch = idProcessoBatch;
	}
	
	public String getFlagEsito() {
		return flagEsito;
	}
	
	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}
	
	public BigDecimal getIdNomeBatch() {
		return idNomeBatch;
	}
	
	public void setIdNomeBatch(BigDecimal idNomeBatch) {
		this.idNomeBatch = idNomeBatch;
	}
	
	public Date getDtInizioElaborazione() {
		return dtInizioElaborazione;
	}
	
	public void setDtInizioElaborazione(Date dtInizioElaborazione) {
		this.dtInizioElaborazione = dtInizioElaborazione;
	}
	


}
