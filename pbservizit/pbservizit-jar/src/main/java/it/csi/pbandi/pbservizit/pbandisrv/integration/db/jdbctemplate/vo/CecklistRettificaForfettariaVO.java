/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class CecklistRettificaForfettariaVO extends GenericVO {
	
	private BigDecimal idAppalto;
	private String oggettoAppalto;
	private BigDecimal idAppaltoChecklist;
	private BigDecimal idTipologiaAppalto;
	private String descTipologiaAppalto;
	private BigDecimal idProceduraAggiudicaz;
	private String codProcAgg;
	private String cigProcAgg;
	private Double importo;
	private Double importoContratto;
	private BigDecimal idEsitoIntermedio;
	private String esitoIntermedio;
	private String flagRettificaIntermedio;
	private BigDecimal idEsitoDefinitivo;
	private String esitoDefinitivo;
	private String flagRettificaDefinitivo;
	private BigDecimal idChecklist;	
	private BigDecimal idDocumentoIndex;
	private String nomeFile;
	private BigDecimal idProgetto;
	private BigDecimal idStatoTipoDocIndex;
	
	public BigDecimal getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}
	public void setIdStatoTipoDocIndex(BigDecimal idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	public String getOggettoAppalto() {
		return oggettoAppalto;
	}
	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}
	public BigDecimal getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}
	public void setIdAppaltoChecklist(BigDecimal idAppaltoChecklist) {
		this.idAppaltoChecklist = idAppaltoChecklist;
	}
	public BigDecimal getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}
	public void setIdTipologiaAppalto(BigDecimal idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}
	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}
	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}
	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public BigDecimal getIdEsitoIntermedio() {
		return idEsitoIntermedio;
	}
	public void setIdEsitoIntermedio(BigDecimal idEsitoIntermedio) {
		this.idEsitoIntermedio = idEsitoIntermedio;
	}
	public String getEsitoIntermedio() {
		return esitoIntermedio;
	}
	public void setEsitoIntermedio(String esitoIntermedio) {
		this.esitoIntermedio = esitoIntermedio;
	}
	public String getFlagRettificaIntermedio() {
		return flagRettificaIntermedio;
	}
	public void setFlagRettificaIntermedio(String flagRettificaIntermedio) {
		this.flagRettificaIntermedio = flagRettificaIntermedio;
	}
	public BigDecimal getIdEsitoDefinitivo() {
		return idEsitoDefinitivo;
	}
	public void setIdEsitoDefinitivo(BigDecimal idEsitoDefinitivo) {
		this.idEsitoDefinitivo = idEsitoDefinitivo;
	}
	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}
	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}
	public String getFlagRettificaDefinitivo() {
		return flagRettificaDefinitivo;
	}
	public void setFlagRettificaDefinitivo(String flagRettificaDefinitivo) {
		this.flagRettificaDefinitivo = flagRettificaDefinitivo;
	}
	public BigDecimal getIdChecklist() {
		return idChecklist;
	}
	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Double getImportoContratto() {
		return importoContratto;
	}
	public void setImportoContratto(Double importoContratto) {
		this.importoContratto = importoContratto;
	}
	
}
