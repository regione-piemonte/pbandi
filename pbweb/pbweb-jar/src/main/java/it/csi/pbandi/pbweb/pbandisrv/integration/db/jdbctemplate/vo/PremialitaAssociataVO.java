/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class PremialitaAssociataVO extends GenericVO {
	
	private BigDecimal idPremialita;
	private BigDecimal progrBandoLineaIntervento;
	private String descrizione;
	private String flagTipoDatoRichiesto;
	private String datoRichiesto;
	private String link;
	
	public BigDecimal getIdPremialita() {
		return idPremialita;
	}
	public void setIdPremialita(BigDecimal idPremialita) {
		this.idPremialita = idPremialita;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFlagTipoDatoRichiesto() {
		return flagTipoDatoRichiesto;
	}
	public void setFlagTipoDatoRichiesto(String flagTipoDatoRichiesto) {
		this.flagTipoDatoRichiesto = flagTipoDatoRichiesto;
	}
	public String getDatoRichiesto() {
		return datoRichiesto;
	}
	public void setDatoRichiesto(String datoRichiesto) {
		this.datoRichiesto = datoRichiesto;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}
