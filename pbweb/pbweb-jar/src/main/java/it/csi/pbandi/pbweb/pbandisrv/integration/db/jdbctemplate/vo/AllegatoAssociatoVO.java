/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class AllegatoAssociatoVO extends GenericVO {
	
	private BigDecimal idAllegato;
	private BigDecimal progrBandoLineaIntervento;
	private String flagObbligatorio;
	private String flagDifferibile;
	private String descrizione;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigDecimal getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(BigDecimal idAllegato) {
		this.idAllegato = idAllegato;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}
	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}
	public String getFlagDifferibile() {
		return flagDifferibile;
	}
	public void setFlagDifferibile(String flagDifferibile) {
		this.flagDifferibile = flagDifferibile;
	}

}
