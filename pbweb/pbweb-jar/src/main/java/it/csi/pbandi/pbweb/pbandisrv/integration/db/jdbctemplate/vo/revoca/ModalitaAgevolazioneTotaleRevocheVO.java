/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ModalitaAgevolazioneTotaleRevocheVO extends GenericVO {
	
	private BigDecimal idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private BigDecimal idProgetto;
	private BigDecimal totaleImportoRevocato;
	private BigDecimal totaleImportoRecuperato;
	
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public void setTotaleImportoRevocato(BigDecimal totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}
	public BigDecimal getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRecuperato(BigDecimal totaleImportoRecuperato) {
		this.totaleImportoRecuperato = totaleImportoRecuperato;
	}
	public BigDecimal getTotaleImportoRecuperato() {
		return totaleImportoRecuperato;
	}
	

}
