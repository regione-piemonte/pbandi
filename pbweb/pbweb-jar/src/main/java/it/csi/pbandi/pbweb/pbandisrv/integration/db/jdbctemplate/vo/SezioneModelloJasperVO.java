/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SezioneModelloJasperVO extends GenericVO {
	
	private BigDecimal progrBandolineaIntervento;
	private BigDecimal idTipoDocumentoIndex;
	private String descBreveTipoDocIndex;
	private String nomeBandolinea; 
	private String descTipoDocIndex;
	private BigDecimal idMacroSezioneModulo;
	private String descBreveMacroSezione;
	private String descMacroSezione ;
	private String reportJrxml;
	private String templateJrxml;
	private String descMicroSezione ;
	private String contenutoMicroSezione;
	private BigDecimal numOrdinamentoMacroSezione;
	private BigDecimal numOrdinamentoMicroSezione;
	
	
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getDescBreveMacroSezione() {
		return descBreveMacroSezione;
	}
	public void setDescBreveMacroSezione(String descBreveMacroSezione) {
		this.descBreveMacroSezione = descBreveMacroSezione;
	}
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
	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}
	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}
	public void setNumOrdinamentoMacroSezione(BigDecimal numOrdinamentoMacroSezione) {
		this.numOrdinamentoMacroSezione = numOrdinamentoMacroSezione;
	}
	public BigDecimal getNumOrdinamentoMacroSezione() {
		return numOrdinamentoMacroSezione;
	}
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public void setReportJrxml(String reportJrxml) {
		this.reportJrxml = reportJrxml;
	}
	public String getReportJrxml() {
		return reportJrxml;
	}
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	public void setTemplateJrxml(String templateJrxml) {
		this.templateJrxml = templateJrxml;
	}
	public String getTemplateJrxml() {
		return templateJrxml;
	}

}
