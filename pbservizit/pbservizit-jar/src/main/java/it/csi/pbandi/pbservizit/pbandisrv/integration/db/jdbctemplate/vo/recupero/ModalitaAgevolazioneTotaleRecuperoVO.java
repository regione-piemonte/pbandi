/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class ModalitaAgevolazioneTotaleRecuperoVO extends GenericVO {
	
	private BigDecimal idModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private BigDecimal idProgetto;
	private BigDecimal totaleImportoRevocato;
	private BigDecimal totaleImportoRecuperato;
	private Date dtUltimaRevoca;
	private String estremiUltimaRevoca;

	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRevocato(BigDecimal totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}
	public BigDecimal getTotaleImportoRecuperato() {
		return totaleImportoRecuperato;
	}
	public void setTotaleImportoRecuperato(BigDecimal totaleImportoRecuperato) {
		this.totaleImportoRecuperato = totaleImportoRecuperato;
	}
	public Date getDtUltimaRevoca() {
		return dtUltimaRevoca;
	}
	public void setDtUltimaRevoca(Date dtUltimaRevoca) {
		this.dtUltimaRevoca = dtUltimaRevoca;
	}
	public String getEstremiUltimaRevoca() {
		return estremiUltimaRevoca;
	}
	public void setEstremiUltimaRevoca(String estremiUltimaRevoca) {
		this.estremiUltimaRevoca = estremiUltimaRevoca;
	}

	



}
