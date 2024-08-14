/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

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
  	
  	private String nomeDocumento;
  	
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && idTipoDocumentoIndex != null && idTarget != null && repository != null && uuidNodo != null && dtInserimentoIndex != null && idEntita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDocumentoIndex != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
//	public String toString() {
//		
//	    String temp = "";
//	    StringBuffer sb = new StringBuffer();
//	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idProgetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idModello);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idModello: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( numProtocollo);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" numProtocollo: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idSoggDelegato);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggDelegato: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idDocumentoIndex);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idSoggRapprLegale);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggRapprLegale: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtVerificaFirma);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtVerificaFirma: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idStatoDocumento);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoDocumento: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( versione);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" versione: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtAggiornamentoIndex);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamentoIndex: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idUtenteIns);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( messageDigest);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" messageDigest: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idTarget);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idTarget: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( repository);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" repository: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( flagFirmaCartacea);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" flagFirmaCartacea: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( nomeFile);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFile: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( uuidNodo);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" uuidNodo: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( noteDocumentoIndex);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" noteDocumentoIndex: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtMarcaTemporale);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtMarcaTemporale: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idUtenteAgg);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtInserimentoIndex);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimentoIndex: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idEntita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
//	    	    
//	    temp = DataFilter.removeNull( idCategAnagraficaMitt);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagraficaMitt: " + temp + "\t\n");
//	    
//	    return sb.toString();
//	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDocumentoIndex");
		
	    return pk;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
}
