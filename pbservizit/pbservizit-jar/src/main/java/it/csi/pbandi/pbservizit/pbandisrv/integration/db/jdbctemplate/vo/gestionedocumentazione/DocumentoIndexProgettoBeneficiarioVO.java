/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DocumentoIndexProgettoBeneficiarioVO extends GenericVO {
	
	private String beneficiario;
	private String codiceErrore;
	private String descErrore;
	private String codiceFiscaleBeneficiario;
	private String codiceVisualizzato;
	private String descStatoDocumentoIndex;
	private String descTipoDocIndex;
	private String descBreveTipoDocIndex;
	private String descBreveTipoAnagrafica;
	private Date dtVerificaFirma;
	private Timestamp dtInserimentoIndex;
	private Date dtMarcaTemporale;
	private String flagFirmabile;
	private String flagFirmaCartacea;
	private BigDecimal idDocumentoIndex;
	private BigDecimal idEntita;
	private BigDecimal idProgetto;
	private BigDecimal idSoggetto;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idStatoDocumento;
	private BigDecimal idTarget;
	private BigDecimal idTipoAnagrafica;
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
	private String nomeFile;
	private String noteDocumentoIndex;
	private BigDecimal progrBandoLineaIntervento;
	private String protocollo;
	private String flagRegolaDemat;
	private String repository;
	private String uuidNodo;
	private BigDecimal idCategAnagraficaMitt;
	private String descCategAnagraficaMitt;
	
	public BigDecimal getIdCategAnagraficaMitt() {
		return idCategAnagraficaMitt;
	}
	public void setIdCategAnagraficaMitt(BigDecimal idCategAnagraficaMitt) {
		this.idCategAnagraficaMitt = idCategAnagraficaMitt;
	}
	public String getDescCategAnagraficaMitt() {
		return descCategAnagraficaMitt;
	}
	public void setDescCategAnagraficaMitt(String descCategAnagraficaMitt) {
		this.descCategAnagraficaMitt = descCategAnagraficaMitt;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getDescErrore() {
		return descErrore;
	}
	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}
	public Date getDtMarcaTemporale() {
		return dtMarcaTemporale;
	}
	public void setDtMarcaTemporale(Date dtMarcaTemporale) {
		this.dtMarcaTemporale = dtMarcaTemporale;
	}
	
	public String getUuidNodo() {
		return uuidNodo;
	}
	public void setUuidNodo(String uuidNodo) {
		this.uuidNodo = uuidNodo;
	}
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNoteDocumentoIndex() {
		return noteDocumentoIndex;
	}
	public void setNoteDocumentoIndex(String noteDocumentoIndex) {
		this.noteDocumentoIndex = noteDocumentoIndex;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
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
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public Timestamp getDtInserimentoIndex() {
		return dtInserimentoIndex;
	}
	public void setDtInserimentoIndex(Timestamp dtInserimentoIndex) {
		this.dtInserimentoIndex = dtInserimentoIndex;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public BigDecimal getIdStatoDocumento() {
		return idStatoDocumento;
	}
	public void setIdStatoDocumento(BigDecimal idStatoDocumento) {
		this.idStatoDocumento = idStatoDocumento;
	}
	public String getFlagFirmaCartacea() {
		return flagFirmaCartacea;
	}
	public void setFlagFirmaCartacea(String flagFirmaCartacea) {
		this.flagFirmaCartacea = flagFirmaCartacea;
	}
	public String getFlagFirmabile() {
		return flagFirmabile;
	}
	public void setFlagFirmabile(String flagFirmabile) {
		this.flagFirmabile = flagFirmabile;
	}
	public Date getDtVerificaFirma() {
		return dtVerificaFirma;
	}
	public void setDtVerificaFirma(Date dtVerificaFirma) {
		this.dtVerificaFirma = dtVerificaFirma;
	}
	public String getFlagRegolaDemat() {
		return flagRegolaDemat;
	}
	public void setFlagRegolaDemat(String flagRegolaDemat) {
		this.flagRegolaDemat = flagRegolaDemat;
	}
	public String getDescStatoDocumentoIndex() {
		return descStatoDocumentoIndex;
	}
	public void setDescStatoDocumentoIndex(String descStatoDocumentoIndex) {
		this.descStatoDocumentoIndex = descStatoDocumentoIndex;
	}
}
