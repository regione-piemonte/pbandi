/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.integration.vo;

import java.math.*;

public class PbandiRDocSpesaProgettoVO {
  	
  	private String task;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idStatoDocumentoSpesa;
  	
  	private BigDecimal idStatoDocumentoSpesaValid;
  	
  	private String noteValidazione;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idDocumentoDiSpesa;
  	
  	private BigDecimal importoRendicontazione;
  	
  	private String tipoInvio;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idAppalto;
  	
	public PbandiRDocSpesaProgettoVO() {}
  	
	public PbandiRDocSpesaProgettoVO (BigDecimal idProgetto, BigDecimal idDocumentoDiSpesa) {
	
		this. idProgetto =  idProgetto;
		this. idDocumentoDiSpesa =  idDocumentoDiSpesa;
	}
  	
	public PbandiRDocSpesaProgettoVO (String task, BigDecimal idUtenteAgg, BigDecimal idStatoDocumentoSpesa, BigDecimal idStatoDocumentoSpesaValid, String noteValidazione, BigDecimal idProgetto, BigDecimal idDocumentoDiSpesa, BigDecimal importoRendicontazione, String tipoInvio, BigDecimal idUtenteIns, BigDecimal idAppalto) {
	
		this. task =  task;
		this. idUtenteAgg =  idUtenteAgg;
		this. idStatoDocumentoSpesa =  idStatoDocumentoSpesa;
		this. idStatoDocumentoSpesaValid =  idStatoDocumentoSpesaValid;
		this. noteValidazione =  noteValidazione;
		this. idProgetto =  idProgetto;
		this. idDocumentoDiSpesa =  idDocumentoDiSpesa;
		this. importoRendicontazione =  importoRendicontazione;
		this. tipoInvio =  tipoInvio;
		this. idUtenteIns =  idUtenteIns;
		this. idAppalto = idAppalto;
	}
  	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public String getTask() {
		return task;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	
	public void setIdStatoDocumentoSpesa(BigDecimal idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	
	public BigDecimal getIdStatoDocumentoSpesaValid() {
		return idStatoDocumentoSpesaValid;
	}
	
	public void setIdStatoDocumentoSpesaValid(BigDecimal idStatoDocumentoSpesaValid) {
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
	}
	
	public String getNoteValidazione() {
		return noteValidazione;
	}
	
	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	
	public BigDecimal getImportoRendicontazione() {
		return importoRendicontazione;
	}
	
	public void setImportoRendicontazione(BigDecimal importoRendicontazione) {
		this.importoRendicontazione = importoRendicontazione;
	}
	
	public String getTipoInvio() {
		return tipoInvio;
	}
	
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
}
