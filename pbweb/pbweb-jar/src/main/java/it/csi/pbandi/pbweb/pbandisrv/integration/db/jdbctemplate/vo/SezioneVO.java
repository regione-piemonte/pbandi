/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SezioneVO extends GenericVO {
	
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal numOrdinamentoMacroSezione;
	private BigDecimal idMacroSezioneModulo;
	private BigDecimal numOrdinamentoMicroSezione;
	private BigDecimal idMicroSezioneModulo;
	private BigDecimal progrBlTipoDocSezMod;
	private String contenutoMicroSezione;
	private String descMicroSezione;
	private String descMacroSezione;
	
	public String getDescMacroSezione() {
		return descMacroSezione;
	}
	public void setDescMacroSezione(String descMacroSezione) {
		this.descMacroSezione = descMacroSezione;
	}
	public String getDescMicroSezione() {
		return descMicroSezione;
	}
	public void setDescMicroSezione(String descMicroSezione) {
		this.descMicroSezione = descMicroSezione;
	}
	public BigDecimal getProgrBlTipoDocSezMod() {
		return progrBlTipoDocSezMod;
	}
	public void setProgrBlTipoDocSezMod(BigDecimal progrBlTipoDocSezMod) {
		this.progrBlTipoDocSezMod = progrBlTipoDocSezMod;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getNumOrdinamentoMacroSezione() {
		return numOrdinamentoMacroSezione;
	}
	public void setNumOrdinamentoMacroSezione(BigDecimal numOrdinamentoMacroSezione) {
		this.numOrdinamentoMacroSezione = numOrdinamentoMacroSezione;
	}
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public BigDecimal getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	public void setIdMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}
	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}

}
