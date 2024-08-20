/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class DocumentoAllegato implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id = null;
	private java.lang.Long idParent = null;
	private java.lang.String nome = null;
	private java.lang.Long protocollo = null;
	private java.lang.Long sizeFile = null;
	private java.lang.Boolean isDisassociabile = null;
	private java.lang.String linkCmd = null;
	private java.lang.String dataInvio = null;
	private java.lang.String descStatoTipoDocIndex = null;

	public DocumentoAllegato() {
		super();
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getIdParent() {
		return idParent;
	}

	public void setIdParent(java.lang.Long idParent) {
		this.idParent = idParent;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.Long getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(java.lang.Long protocollo) {
		this.protocollo = protocollo;
	}

	public java.lang.Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(java.lang.Long sizeFile) {
		this.sizeFile = sizeFile;
	}

	public java.lang.Boolean getIsDisassociabile() {
		return isDisassociabile;
	}

	public void setIsDisassociabile(java.lang.Boolean isDisassociabile) {
		this.isDisassociabile = isDisassociabile;
	}

	public java.lang.String getLinkCmd() {
		return linkCmd;
	}

	public void setLinkCmd(java.lang.String linkCmd) {
		this.linkCmd = linkCmd;
	}

	public java.lang.String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(java.lang.String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public java.lang.String getDescStatoTipoDocIndex() {
		return descStatoTipoDocIndex;
	}

	public void setDescStatoTipoDocIndex(java.lang.String descStatoTipoDocIndex) {
		this.descStatoTipoDocIndex = descStatoTipoDocIndex;
	}

}
