/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentoCertificazioneVO {
	private String codiceProgettoVisualizzato;
	private BigDecimal idProgetto;
	private BigDecimal idLineaDiIntervento;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idPropostaCertificaz;
	private String descTipoDocIndexStato;
	private String descBreveStatoPropostaCert;
	private String descTipoDocIndex;
	private String descBreveTipoDocIndex;
	private BigDecimal idDocumentoIndex;
	private String nomeDocumento;
	private Date dtInserimentoIndex;
	private String noteDocumentoIndex;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setCodiceProgettoVisualizzato(String codiceProgettoVisualizzato) {
		this.codiceProgettoVisualizzato = codiceProgettoVisualizzato;
	}
	public String getCodiceProgettoVisualizzato() {
		return codiceProgettoVisualizzato;
	}
	public void setDescTipoDocIndexStato(String descTipoDocIndexStato) {
		this.descTipoDocIndexStato = descTipoDocIndexStato;
	}
	public String getDescTipoDocIndexStato() {
		return descTipoDocIndexStato;
	}
	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}
	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public Date getDtInserimentoIndex() {
		return dtInserimentoIndex;
	}
	public void setDtInserimentoIndex(Date dtInserimentoIndex) {
		this.dtInserimentoIndex = dtInserimentoIndex;
	}
	public String getNoteDocumentoIndex() {
		return noteDocumentoIndex;
	}
	public void setNoteDocumentoIndex(String noteDocumentoIndex) {
		this.noteDocumentoIndex = noteDocumentoIndex;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
}
