/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoVO extends GenericVO {
	
	private Long idProgetto = null;
	private String titoloProgetto = null;
	private String codiceProgetto = null;
	private Double totaleQuietanzato = null;
	private String cup = null;
	private Date dtConcessione = null;
	private BigDecimal sogliaSpesaCalcErogazioni = null;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long progetto) {
		this.idProgetto = progetto;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String progetto) {
		this.titoloProgetto = progetto;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String progetto) {
		this.codiceProgetto = progetto;
	}
	public Double getTotaleQuietanzato() {
		return totaleQuietanzato;
	}
	public void setTotaleQuietanzato(Double quietanzato) {
		this.totaleQuietanzato = quietanzato;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getCup() {
		return cup;
	}
	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}
	public Date getDtConcessione() {
		return dtConcessione;
	}
	public void setSogliaSpesaCalcErogazioni(BigDecimal sogliaSpesaCalcErogazioni) {
		this.sogliaSpesaCalcErogazioni = sogliaSpesaCalcErogazioni;
	}
	public BigDecimal getSogliaSpesaCalcErogazioni() {
		return sogliaSpesaCalcErogazioni;
	}
	
	
	
	

}
