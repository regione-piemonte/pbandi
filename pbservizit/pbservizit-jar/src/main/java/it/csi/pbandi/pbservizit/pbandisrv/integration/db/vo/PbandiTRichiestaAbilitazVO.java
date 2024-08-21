/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;



public class PbandiTRichiestaAbilitazVO extends GenericVO {

  	
  	private BigDecimal progrSoggettoProgetto;
  	
  	private BigDecimal progrSoggettiCorrelati;
  	
  	private Date dtRichiesta;
  	
  	private String esito;
  	
  	private Date dtEsito;
  	
  	private Long idUtenteIns;
  	
  	private Long idUtenteAgg;
  	
  	private String accessoSistema;
  	
	public PbandiTRichiestaAbilitazVO() {}
	
	

	public PbandiTRichiestaAbilitazVO(BigDecimal progrSoggettoProgetto, BigDecimal progrSoggettiCorrelati,
			Date dtRichiesta, String esito, Date dtEsito, Long idUtenteIns,
			Long idUtenteAgg, String accessoSistema) {
		super();
		this.progrSoggettoProgetto = progrSoggettoProgetto;
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
		this.dtRichiesta = dtRichiesta;
		this.esito = esito;
		this.dtEsito = dtEsito;
		this.idUtenteIns = idUtenteIns;
		this.idUtenteAgg = idUtenteAgg;
		this.accessoSistema = accessoSistema;
	}

	
	


	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}



	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}



	public BigDecimal getProgrSoggettiCorrelati() {
		return progrSoggettiCorrelati;
	}



	public void setProgrSoggettiCorrelati(BigDecimal progrSoggettiCorrelati) {
		this.progrSoggettiCorrelati = progrSoggettiCorrelati;
	}



	public Date getDtRichiesta() {
		return dtRichiesta;
	}



	public void setDtRichiesta(Date dtRichiesta) {
		this.dtRichiesta = dtRichiesta;
	}



	public String getEsito() {
		return esito;
	}



	public void setEsito(String esito) {
		this.esito = esito;
	}



	public Date getDtEsito() {
		return dtEsito;
	}



	public void setDtEsito(Date dtEsito) {
		this.dtEsito = dtEsito;
	}



	public Long getIdUtenteIns() {
		return idUtenteIns;
	}



	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}



	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}



	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}



	public boolean isPKValid() {
		boolean isPkValid = false;
		
		if (progrSoggettiCorrelati != null && progrSoggettoProgetto != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public boolean isValid() {
		boolean isValid = false;
		if (progrSoggettiCorrelati != null && progrSoggettoProgetto != null) {
			isValid = true;
   		}
   		return isValid;
	}



	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();	
		pk.add("progrSoggettiCorrelati");	
		pk.add("progrSoggettoProgetto");	
    return pk;
	}



	public String getAccessoSistema() {
		return accessoSistema;
	}



	public void setAccessoSistema(String accessoSistema) {
		this.accessoSistema = accessoSistema;
	}
	
	
	
	
}
