/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class TemplateJasperVO extends GenericVO {
	
	
	private BigDecimal idTemplate;
    private BigDecimal idTipoDocumentoIndex;
    private String descBreveTipoDocIndex;
    private String descTipoDocIndex;
    private BigDecimal progrBandolineaIntervento;
    private String nomeBandolinea;
    private String nomeTemplate;
    private BigDecimal idMacroSezioneModulo;
    private String descBreveMacroSezione;
    private String sezDsParamName;
    private String sezReportParamName;
    private String subDsParamName;
    private String subReportParamName;
    private BigDecimal idTipoTemplate;
    private String descBreveTipoTemplate;
    private Date dtInserimento;
    
    private byte[] jasperblob;
	public BigDecimal getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(BigDecimal idTemplate) {
		this.idTemplate = idTemplate;
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
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}
	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}
	public String getNomeTemplate() {
		return nomeTemplate;
	}
	public void setNomeTemplate(String nomeTemplate) {
		this.nomeTemplate = nomeTemplate;
	}
	public String getSezDsParamName() {
		return sezDsParamName;
	}
	public void setSezDsParamName(String sezDsParamName) {
		this.sezDsParamName = sezDsParamName;
	}
	public String getSezReportParamName() {
		return sezReportParamName;
	}
	public void setSezReportParamName(String sezReportParamName) {
		this.sezReportParamName = sezReportParamName;
	}
	public String getSubDsParamName() {
		return subDsParamName;
	}
	public void setSubDsParamName(String subDsParamName) {
		this.subDsParamName = subDsParamName;
	}
	public String getSubReportParamName() {
		return subReportParamName;
	}
	public void setSubReportParamName(String subReportParamName) {
		this.subReportParamName = subReportParamName;
	}
	public BigDecimal getIdTipoTemplate() {
		return idTipoTemplate;
	}
	public void setIdTipoTemplate(BigDecimal idTipoTemplate) {
		this.idTipoTemplate = idTipoTemplate;
	}
	public String getDescBreveTipoTemplate() {
		return descBreveTipoTemplate;
	}
	public void setDescBreveTipoTemplate(String descBreveTipoTemplate) {
		this.descBreveTipoTemplate = descBreveTipoTemplate;
	}
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	public void setDescBreveMacroSezione(String descBreveMacroSezione) {
		this.descBreveMacroSezione = descBreveMacroSezione;
	}
	public String getDescBreveMacroSezione() {
		return descBreveMacroSezione;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setJasperblob(byte[] jasperblob) {
		this.jasperblob = jasperblob;
	}
	public byte[] getJasperblob() {
		return jasperblob;
	}
	@Override
	public String toString() {
		return "TemplateJasperVO [idTemplate=" + idTemplate + ", idTipoDocumentoIndex=" + idTipoDocumentoIndex
				+ ", descBreveTipoDocIndex=" + descBreveTipoDocIndex + ", descTipoDocIndex=" + descTipoDocIndex
				+ ", progrBandolineaIntervento=" + progrBandolineaIntervento + ", nomeBandolinea=" + nomeBandolinea
				+ ", nomeTemplate=" + nomeTemplate + ", idMacroSezioneModulo=" + idMacroSezioneModulo
				+ ", descBreveMacroSezione=" + descBreveMacroSezione + ", sezDsParamName=" + sezDsParamName
				+ ", sezReportParamName=" + sezReportParamName + ", subDsParamName=" + subDsParamName
				+ ", subReportParamName=" + subReportParamName + ", idTipoTemplate=" + idTipoTemplate
				+ ", descBreveTipoTemplate=" + descBreveTipoTemplate + ", dtInserimento=" + dtInserimento + "]";
	}

}
