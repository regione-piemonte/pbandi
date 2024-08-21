/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager;

public class DocumentoContabileDTO {
	
	private java.util.Date dataDocumento = null;
	private java.util.Date dataPagamento = null;
	private java.util.Date dataValuta = null;
	private java.lang.String descDocumento = null;
	private java.lang.String destinatarioPagamento = null;
	private String flagElettronico = null;
	private java.lang.Long idDocumentoDiSpesa = null;
	private java.lang.Long idPagamento = null;
	private java.lang.Double imponibile = null;
	private java.lang.Double importoIva = null;
	private java.lang.Double importoPagamento = null;
	private java.lang.Double importoRendicontabile = null;
	private java.lang.Double importoTotaleDocumento = null;
	private java.lang.String modalitaPagamento = null;
	private java.lang.String numProtocollo= null;
	private java.lang.String numeroDocumento = null;
	private java.lang.String task = null;
	private java.lang.String tipoDocumento = null;
	private String tipoInvio = null;
	
	//PK 05/2023 bandi Arch. Rurali
	private String ruolo = null;
	private java.util.Date dataFirmaContratto = null;
	
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(java.lang.Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(java.lang.Long idPagamento) {
		this.idPagamento = idPagamento;
	}
	public java.lang.String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(java.lang.String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public java.lang.String getModalitaPagamento() {
		return modalitaPagamento;
	}
	public void setModalitaPagamento(java.lang.String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}
	public java.lang.String getDestinatarioPagamento() {
		return destinatarioPagamento;
	}
	public void setDestinatarioPagamento(java.lang.String destinatarioPagamento) {
		this.destinatarioPagamento = destinatarioPagamento;
	}
	public java.lang.Double getImponibile() {
		return imponibile;
	}
	public void setImponibile(java.lang.Double imponibile) {
		this.imponibile = imponibile;
	}
	public java.lang.Double getImportoIva() {
		return importoIva;
	}
	public void setImportoIva(java.lang.Double importoIva) {
		this.importoIva = importoIva;
	}
	public java.lang.Double getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	public void setImportoTotaleDocumento(java.lang.Double importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	public java.lang.Double getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(java.lang.Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public java.util.Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(java.util.Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public java.util.Date getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(java.util.Date dataValuta) {
		this.dataValuta = dataValuta;
	}
	public java.util.Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(java.util.Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public java.lang.String getTask() {
		return task;
	}
	public void setTask(java.lang.String task) {
		this.task = task;
	}
	public java.lang.Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public void setImportoRendicontabile(java.lang.Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public java.lang.String getDescDocumento() {
		return descDocumento;
	}
	public void setDescDocumento(java.lang.String descDocumento) {
		this.descDocumento = descDocumento;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public java.lang.String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(java.lang.String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public String getFlagElettronico() {
		return flagElettronico;
	}
	public void setFlagElettronico(String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public java.util.Date getDataFirmaContratto() {
		return dataFirmaContratto;
	}
	public void setDataFirmaContratto(java.util.Date dataFirmaContratto) {
		this.dataFirmaContratto = dataFirmaContratto;
	}

}
