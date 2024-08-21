/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.integration.vo;

import java.beans.IntrospectionException;
import java.math.*;
import java.sql.Date;
import java.util.Set;
import it.csi.pbandi.pbservizit.util.BeanUtil;

public class PbandiTDocumentoIndexVO {

  	private BigDecimal idProgetto;
  	
  	private BigDecimal idModello;
  	
  	private String numProtocollo;
  	
  	private BigDecimal idSoggDelegato;
  	
  	private BigDecimal idDocumentoIndex;
  	
  	private BigDecimal idSoggRapprLegale;
  	
  	private Date dtVerificaFirma;
  	
  	private BigDecimal idStatoDocumento;
  	
  	private BigDecimal versione;
  	
  	private Date dtAggiornamentoIndex;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private String messageDigest;
  	
  	private BigDecimal idTarget;
  	
  	private String repository;
  	
  	private String flagFirmaCartacea;
  	
  	private String nomeFile;
  	
  	private String uuidNodo;
  	
  	private String noteDocumentoIndex;
  	
  	private Date dtMarcaTemporale;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInserimentoIndex;
  	
  	private BigDecimal idEntita;
  	
  	private BigDecimal idCategAnagraficaMitt;
  	
  	private String nomeDocumento;					// nome univoco del file su file system
  	
  	private String nomeDocumentoFirmato;			// nome univoco del file firmato digitalmente su file system
  	
  	private String nomeDocumentoMarcato;			// nome univoco del file marcato temporalmente su file system
  	
	public PbandiTDocumentoIndexVO() {}
  	
	public PbandiTDocumentoIndexVO (BigDecimal idDocumentoIndex) {
	
		this. idDocumentoIndex =  idDocumentoIndex;
	}
  	
	public PbandiTDocumentoIndexVO (BigDecimal idProgetto, BigDecimal idModello, String numProtocollo, BigDecimal idSoggDelegato, BigDecimal idDocumentoIndex, BigDecimal idSoggRapprLegale, Date dtVerificaFirma, BigDecimal idStatoDocumento, BigDecimal versione, Date dtAggiornamentoIndex, BigDecimal idUtenteIns, BigDecimal idTipoDocumentoIndex, String messageDigest, BigDecimal idTarget, String repository, String flagFirmaCartacea, String nomeFile, String uuidNodo, String noteDocumentoIndex, Date dtMarcaTemporale, BigDecimal idUtenteAgg, Date dtInserimentoIndex, BigDecimal idEntita, BigDecimal idCategAnagraficaMitt) {
		this. idProgetto =  idProgetto;
		this. idModello =  idModello;
		this. numProtocollo =  numProtocollo;
		this. idSoggDelegato =  idSoggDelegato;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. idSoggRapprLegale =  idSoggRapprLegale;
		this. dtVerificaFirma =  dtVerificaFirma ;
		this. idStatoDocumento =  idStatoDocumento;
		this. versione =  versione;
		this. dtAggiornamentoIndex =  dtAggiornamentoIndex;
		this. idUtenteIns =  idUtenteIns;
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. messageDigest =  messageDigest;
		this. idTarget =  idTarget;
		this. repository =  repository;
		this. flagFirmaCartacea =  flagFirmaCartacea;
		this. nomeFile =  nomeFile;
		this. uuidNodo =  uuidNodo;
		this. noteDocumentoIndex =  noteDocumentoIndex;
		this. dtMarcaTemporale =  dtMarcaTemporale;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInserimentoIndex =  dtInserimentoIndex;
		this. idEntita =  idEntita;
		this. idCategAnagraficaMitt = idCategAnagraficaMitt; 
	}
  	
	public PbandiTDocumentoIndexVO(
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO documentoIndexVO) {
		this. idProgetto =  documentoIndexVO.getIdProgetto();
		this. idModello =  documentoIndexVO.getIdModello();
		this. numProtocollo =  documentoIndexVO.getNumProtocollo();
		this. idSoggDelegato =  documentoIndexVO.getIdSoggDelegato();
		this. idDocumentoIndex =  documentoIndexVO.getIdDocumentoIndex();
		this. idSoggRapprLegale =  documentoIndexVO.getIdSoggRapprLegale();
		this. dtVerificaFirma =  documentoIndexVO.getDtVerificaFirma() ;
		this. idStatoDocumento =  documentoIndexVO.getIdStatoDocumento();
		this. versione =  documentoIndexVO.getVersione();
		this. dtAggiornamentoIndex =  documentoIndexVO.getDtAggiornamentoIndex();
		this. idUtenteIns =  documentoIndexVO.getIdUtenteIns();
		this. idTipoDocumentoIndex =  documentoIndexVO.getIdTipoDocumentoIndex();
		this. messageDigest =  documentoIndexVO.getMessageDigest();
		this. idTarget =  documentoIndexVO.getIdTarget();
		this. repository =  documentoIndexVO.getRepository();
		this. flagFirmaCartacea =  documentoIndexVO.getFlagFirmaCartacea();
		this. nomeFile =  documentoIndexVO.getNomeFile();
		this. uuidNodo =  documentoIndexVO.getUuidNodo();
		this. noteDocumentoIndex =  documentoIndexVO.getNoteDocumentoIndex();
		this. dtMarcaTemporale =  documentoIndexVO.getDtMarcaTemporale();
		this. idUtenteAgg =  documentoIndexVO.getIdUtenteAgg();
		this. dtInserimentoIndex =  documentoIndexVO.getDtInserimentoIndex();
		this. idEntita =  documentoIndexVO.getIdEntita();
		this. idCategAnagraficaMitt = documentoIndexVO.getIdCategAnagraficaMitt(); 
	}

	public BigDecimal getIdCategAnagraficaMitt() {
		return idCategAnagraficaMitt;
	}

	public void setIdCategAnagraficaMitt(BigDecimal idCategAnagraficaMitt) {
		this.idCategAnagraficaMitt = idCategAnagraficaMitt;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdModello() {
		return idModello;
	}
	
	public void setIdModello(BigDecimal idModello) {
		this.idModello = idModello;
	}
	
	public String getNumProtocollo() {
		return numProtocollo;
	}
	
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	
	public BigDecimal getIdSoggDelegato() {
		return idSoggDelegato;
	}
	
	public void setIdSoggDelegato(BigDecimal idSoggDelegato) {
		this.idSoggDelegato = idSoggDelegato;
	}
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	public BigDecimal getIdSoggRapprLegale() {
		return idSoggRapprLegale;
	}
	
	public void setIdSoggRapprLegale(BigDecimal idSoggRapprLegale) {
		this.idSoggRapprLegale = idSoggRapprLegale;
	}
	
	public Date getDtVerificaFirma() {
		return dtVerificaFirma;
	}
	
	public void setDtVerificaFirma(Date dtVerificaFirma) {
		this.dtVerificaFirma = dtVerificaFirma;
	}
	
	public BigDecimal getIdStatoDocumento() {
		return idStatoDocumento;
	}
	
	public void setIdStatoDocumento(BigDecimal idStatoDocumento) {
		this.idStatoDocumento = idStatoDocumento;
	}
	
	public BigDecimal getVersione() {
		return versione;
	}
	
	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}
	
	public Date getDtAggiornamentoIndex() {
		return dtAggiornamentoIndex;
	}
	
	public void setDtAggiornamentoIndex(Date dtAggiornamentoIndex) {
		this.dtAggiornamentoIndex = dtAggiornamentoIndex;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public String getMessageDigest() {
		return messageDigest;
	}
	
	public void setMessageDigest(String messageDigest) {
		this.messageDigest = messageDigest;
	}
	
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	
	public String getRepository() {
		return repository;
	}
	
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	public String getFlagFirmaCartacea() {
		return flagFirmaCartacea;
	}
	
	public void setFlagFirmaCartacea(String flagFirmaCartacea) {
		this.flagFirmaCartacea = flagFirmaCartacea;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public String getUuidNodo() {
		return uuidNodo;
	}
	
	public void setUuidNodo(String uuidNodo) {
		this.uuidNodo = uuidNodo;
	}
	
	public String getNoteDocumentoIndex() {
		return noteDocumentoIndex;
	}
	
	public void setNoteDocumentoIndex(String noteDocumentoIndex) {
		this.noteDocumentoIndex = noteDocumentoIndex;
	}
	
	public Date getDtMarcaTemporale() {
		return dtMarcaTemporale;
	}
	
	public void setDtMarcaTemporale(Date dtMarcaTemporale) {
		this.dtMarcaTemporale = dtMarcaTemporale;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInserimentoIndex() {
		return dtInserimentoIndex;
	}
	
	public void setDtInserimentoIndex(Date dtInserimentoIndex) {
		this.dtInserimentoIndex = dtInserimentoIndex;
	}
	
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getNomeDocumentoFirmato() {
		return nomeDocumentoFirmato;
	}

	public void setNomeDocumentoFirmato(String nomeDocumentoFirmato) {
		this.nomeDocumentoFirmato = nomeDocumentoFirmato;
	}

	public String getNomeDocumentoMarcato() {
		return nomeDocumentoMarcato;
	}

	public void setNomeDocumentoMarcato(String nomeDocumentoMarcato) {
		this.nomeDocumentoMarcato = nomeDocumentoMarcato;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+" : ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}	
	
}
