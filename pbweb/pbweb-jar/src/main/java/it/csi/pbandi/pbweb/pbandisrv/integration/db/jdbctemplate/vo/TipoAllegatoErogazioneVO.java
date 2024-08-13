/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TipoAllegatoErogazioneVO extends GenericVO {
	private String descMicroSezione;
	private String descTipoAllegato;
	private BigDecimal idCausaleErogazione;
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal numOrdinamentoMacroSezione ;
	private BigDecimal numOrdinamentoMicroSezione;
	private BigDecimal progrBandoLineaIntervento;

	public BigDecimal getNumOrdinamentoMacroSezione() {
		return numOrdinamentoMacroSezione;
	}
	public void setNumOrdinamentoMacroSezione(BigDecimal numOrdinamentoMacroSezione) {
		this.numOrdinamentoMacroSezione = numOrdinamentoMacroSezione;
	}
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public String getDescTipoAllegato() {
		return descTipoAllegato;
	}
	public void setDescTipoAllegato(String descTipoAllegato) {
		this.descTipoAllegato = descTipoAllegato;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getDescMicroSezione() {
		return descMicroSezione;
	}
	public void setDescMicroSezione(String descMicroSezione) {
		this.descMicroSezione = descMicroSezione;
	}
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
 
}