/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettaglioOperazioneAutomaticaValidazioneVO extends GenericVO {
	private String descStato;
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal numDocumenti;
	
	public String getDescStato() {
		return descStato;
	}
	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getNumDocumenti() {
		return numDocumenti;
	}
	public void setNumDocumenti(BigDecimal numDocumenti) {
		this.numDocumenti = numDocumenti;
	}
	
	

}
